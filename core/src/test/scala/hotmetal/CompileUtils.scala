package hotmetal



object CompileUtils:

  transparent inline def compileErrors(inline code: String): Option[String] =
    val errors = scala.compiletime.testing.typeCheckErrors(code)
    if errors.isEmpty then None
    else Some(errors.mkString("\n"))

trait CompileUtils:
  self: munit.Assertions =>

  transparent inline def expectCompileError(subMessage: String)(inline code: String): Unit =
    CompileUtils.compileErrors(code) match
      case None =>
        throw new Exception("Expected a compile error but no errors were found")
      case Some(errors) =>
        assert(
          errors.contains(subMessage),
          clue = errors
        )
