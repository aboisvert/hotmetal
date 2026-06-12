package hotmetal

import java.lang.StringBuilder

private[hotmetal] object HtmlUtils:

  /** Escape HTML characters that can cause XSS, according to OWASP specification:
    * https://cheatsheetseries.owasp.org/cheatsheets/Cross_Site_Scripting_Prevention_Cheat_Sheet.html#output-encoding-rules-summary
    *
    * Specification:
    *   - Convert & to &amp;
    *   - Convert < to &lt;
    *   - Convert > to &gt;
    *   - Convert " to &quot;
    *   - Convert ' to &apos;
    *   - Convert / to &#47;
    *
    * Suitable for escaping text content (text nodes, script content, etc).
    *
    * @param chars
    *   char sequence (e.g., string) that needs HTML encoding
    * @param optimize
    *   if true, the function will check the original string for any dangerous characters and return
    *   it as is if it doesn't contain any. This is a generally a performance optimization and
    *   therefore the default is true. If you know the string contains dangerous characters, set
    *   this to false.
    * @return
    *   HTML encoded string
    */
  final def escapeHtml(chars: CharSequence, optimize: Boolean = true): CharSequence =
    escapeHtmlImpl(chars, escapeSlash = true, escapeApostrophe = true, optimize)

  /** Escape characters dangerous in double-quoted HTML attribute values.
    *
    * Hotmetal always emits double-quoted attributes (`name="value"`). In that context:
    *   - Escape `&`, `<`, `>`, and `"` to prevent XSS and attribute-value breakout.
    *   - Leave `'` literal (it does not terminate a double-quoted value).
    *   - Leave `/` literal to preserve URL path readability (e.g. `/login`).
    */
  final def escapeHtmlAttr(chars: CharSequence, optimize: Boolean = true): CharSequence =
    escapeHtmlImpl(chars, escapeSlash = false, escapeApostrophe = false, optimize)

  private final def escapeHtmlImpl(
      chars: CharSequence,
      escapeSlash: Boolean,
      escapeApostrophe: Boolean,
      optimize: Boolean
  ): CharSequence =
    if optimize && !containsDangerous(chars, escapeSlash, escapeApostrophe) then return chars
    val sb = new StringBuilder(chars.length)
    var idx = 0
    while idx < chars.length do
      val c = chars.charAt(idx)
      val escaped = escapeCharOrNull(c, escapeSlash, escapeApostrophe)
      if escaped != null then sb.append(escaped)
      else sb.append(c)
      idx += 1
    sb
  end escapeHtmlImpl

  private final def containsDangerous(
      cs: CharSequence,
      checkSlash: Boolean,
      checkApostrophe: Boolean
  ): Boolean =
    var idx = 0
    while idx < cs.length do
      val dangerous = cs.charAt(idx) match
        case '&'  => true
        case '<'  => true
        case '>'  => true
        case '"'  => true
        case '\'' => checkApostrophe
        case '/'  => checkSlash
        case _    => false
      if dangerous then return true
      idx += 1
    return false
  end containsDangerous

  private final def escapeCharOrNull(
      char: Char,
      escapeSlash: Boolean,
      escapeApostrophe: Boolean
  ): String = char match
    case '&'  => "&amp;"
    case '<'  => "&lt;"
    case '>'  => "&gt;"
    case '"'  => "&quot;"
    case '\'' => if escapeApostrophe then "&apos;" else null
    case '/'  => if escapeSlash then "&#47;" else null
    case _    => null
  end escapeCharOrNull

end HtmlUtils
