package hotmetal

/** Test helpers for comparing rendered HTML while ignoring incidental whitespace. */
trait HtmlAssertions:
  self: munit.Assertions =>

  def assertHtmlEquals(obtained: String, expected: String): Unit =
    assertNoDiff(
      HtmlCanonicalizer.canonicalize(obtained),
      HtmlCanonicalizer.canonicalize(expected)
    )

private object HtmlCanonicalizer:
  private val RawElements = Set("script", "style", "pre", "textarea")

  def canonicalize(html: String): String =
    val out = new java.lang.StringBuilder(html.length)
    var i = 0
    while i < html.length do
      if html.charAt(i) == '<' then
        val tagEnd = tagEndIndex(html, i)
        val tag = html.substring(i, tagEnd)
        out.append(canonicalizeTag(tag))
        rawElementName(tag) match
          case Some(name) if !isClosingTag(tag) =>
            val closeStart = findClosingTagStart(html, tagEnd, name)
            out.append(html.substring(tagEnd, closeStart))
            val closeEnd = tagEndIndex(html, closeStart)
            out.append(canonicalizeTag(html.substring(closeStart, closeEnd)))
            i = closeEnd
          case _ =>
            i = tagEnd
      else
        val nextTag = html.indexOf('<', i)
        val textEnd = if nextTag == -1 then html.length else nextTag
        val text = html.substring(i, textEnd)
        if text.nonEmpty && !text.forall(_.isWhitespace) then out.append(text)
        i = textEnd
    out.toString

  private def tagEndIndex(html: String, start: Int): Int =
    var inQuote = 0.toChar
    var i = start + 1
    while i < html.length do
      val c = html.charAt(i)
      if inQuote != 0 then
        if c == inQuote then inQuote = 0
      else if c == '"' || c == '\'' then
        inQuote = c
      else if c == '>' then
        return i + 1
      i += 1
    html.length

  private def isClosingTag(tag: String): Boolean =
    tag.length > 2 && tag.charAt(1) == '/'

  private def rawElementName(tag: String): Option[String] =
    if !tag.startsWith("<") || isClosingTag(tag) || tag.startsWith("<!") then None
    else
      val nameStart = 1
      val nameEnd = tag.indexWhere(c => c.isWhitespace || c == '>' || c == '/', nameStart)
      val end = if nameEnd == -1 then tag.length else nameEnd
      val name = tag.substring(nameStart, end).toLowerCase
      if RawElements.contains(name) then Some(name) else None

  private def findClosingTagStart(html: String, from: Int, name: String): Int =
    val needle = s"</$name"
    var idx = from
    while idx < html.length do
      val hit = html.indexOf(needle, idx)
      if hit == -1 then throw new IllegalArgumentException(s"Missing closing tag </$name>")
      val after = hit + needle.length
      if after >= html.length || html.charAt(after).isWhitespace || html.charAt(after) == '>' then
        return hit
      idx = hit + 1
    throw new IllegalArgumentException(s"Missing closing tag </$name>")

  private def canonicalizeTag(tag: String): String =
    if tag.isEmpty then return tag
    val out = new java.lang.StringBuilder(tag.length)
    var i = 0
    var inQuote = 0.toChar
    var pendingSpace = false

    def flushSpace(): Unit =
      if pendingSpace && out.length > 0 && out.charAt(out.length - 1) != ' ' && out.charAt(out.length - 1) != '<' then
        out.append(' ')
      pendingSpace = false

    def trimTrailingSpace(): Unit =
      if out.length > 0 && out.charAt(out.length - 1) == ' ' then out.setLength(out.length - 1)

    while i < tag.length do
      val c = tag.charAt(i)
      if inQuote != 0 then
        out.append(c)
        if c == inQuote then inQuote = 0
        i += 1
      else if c == '"' || c == '\'' then
        flushSpace()
        inQuote = c
        out.append(c)
        i += 1
      else if c == '/' && i + 1 < tag.length && tag.charAt(i + 1) == '>' then
        trimTrailingSpace()
        out.append("/>")
        return out.toString
      else if c == '>' then
        trimTrailingSpace()
        out.append('>')
        return out.toString
      else if c.isWhitespace then
        if out.length > 0 && out.charAt(out.length - 1) != '<' then pendingSpace = true
        i += 1
        while i < tag.length && tag.charAt(i).isWhitespace do i += 1
      else
        flushSpace()
        while i < tag.length do
          val next = tag.charAt(i)
          if inQuote != 0 then
            out.append(next)
            if next == inQuote then inQuote = 0
            i += 1
          else if next == '"' || next == '\'' then
            inQuote = next
            out.append(next)
            i += 1
          else if next == '/' && i + 1 < tag.length && tag.charAt(i + 1) == '>' then
            trimTrailingSpace()
            out.append("/>")
            return out.toString
          else if next == '>' then
            trimTrailingSpace()
            out.append('>')
            return out.toString
          else if next.isWhitespace then
            if out.length > 0 && out.charAt(out.length - 1) != '<' then pendingSpace = true
            i += 1
            while i < tag.length && tag.charAt(i).isWhitespace do i += 1
          else
            flushSpace()
            out.append(next)
            i += 1
    out.toString
