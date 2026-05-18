package hotmetal.samples

import java.nio.charset.StandardCharsets.UTF_8
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

object WriteSamplePages:
  def main(args: Array[String]): Unit =
    val outputDir =
      if args.nonEmpty then Paths.get(args(0))
      else Paths.get("samples", "generated-pages")

    Files.createDirectories(outputDir)

    for page <- SamplePages.all do
      val outputPath = outputDir.resolve(s"${page.name}.html")
      Files.writeString(outputPath, page.render().toString, UTF_8)
      println(s"Wrote ${normalize(outputPath)}")

  private def normalize(path: Path): String =
    path.toAbsolutePath.normalize.toString

