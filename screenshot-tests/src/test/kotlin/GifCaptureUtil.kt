package cash.turbine.circom.docs

import java.io.File
import java.util.concurrent.TimeUnit

object GifCaptureUtil {

    private const val DEFAULT_FPS = 15
    private const val DEFAULT_WIDTH = 800

    fun captureGif(
        outputPath: String,
        durationSeconds: Int = 3,
        fps: Int = DEFAULT_FPS,
        width: Int = DEFAULT_WIDTH,
        action: () -> Unit
    ): File {
        val tempVideo = File.createTempFile("recording", ".mp4")
        val outputFile = File(outputPath)

        try {
            // Start screen recording using ffmpeg with avfoundation (macOS)
            val recordProcess = ProcessBuilder(
                "ffmpeg", "-y",
                "-f", "avfoundation",
                "-framerate", fps.toString(),
                "-i", "1:none",  // Screen capture, no audio
                "-t", durationSeconds.toString(),
                "-vf", "scale=$width:-1",
                "-c:v", "libx264",
                "-preset", "ultrafast",
                "-pix_fmt", "yuv420p",
                tempVideo.absolutePath
            ).redirectErrorStream(true).start()

            // Wait for ffmpeg to initialize
            Thread.sleep(800)

            // Execute the action to record
            action()

            // Wait for recording to complete
            val completed = recordProcess.waitFor((durationSeconds + 5).toLong(), TimeUnit.SECONDS)
            if (!completed) {
                recordProcess.destroyForcibly()
            }

            // Convert to optimized GIF
            convertToGif(tempVideo, outputFile, fps, width)

            return outputFile
        } finally {
            tempVideo.delete()
        }
    }

    private fun convertToGif(input: File, output: File, fps: Int, width: Int) {
        // Two-pass GIF conversion for better quality
        val paletteFile = File.createTempFile("palette", ".png")

        try {
            // Generate palette
            ProcessBuilder(
                "ffmpeg", "-y",
                "-i", input.absolutePath,
                "-vf", "fps=$fps,scale=$width:-1:flags=lanczos,palettegen=stats_mode=diff",
                paletteFile.absolutePath
            ).redirectErrorStream(true).start().waitFor(30, TimeUnit.SECONDS)

            // Generate GIF using palette
            ProcessBuilder(
                "ffmpeg", "-y",
                "-i", input.absolutePath,
                "-i", paletteFile.absolutePath,
                "-filter_complex", "fps=$fps,scale=$width:-1:flags=lanczos[x];[x][1:v]paletteuse=dither=bayer:bayer_scale=5:diff_mode=rectangle",
                output.absolutePath
            ).redirectErrorStream(true).start().waitFor(60, TimeUnit.SECONDS)
        } finally {
            paletteFile.delete()
        }
    }

    fun captureGifWithRobot(
        robot: com.intellij.remoterobot.RemoteRobot,
        outputPath: String,
        durationSeconds: Int = 3,
        fps: Int = DEFAULT_FPS,
        action: () -> Unit
    ): BaseScreenshotTest.ScreenshotMetadata {
        val file = captureGif(outputPath, durationSeconds, fps, DEFAULT_WIDTH, action)

        return BaseScreenshotTest.ScreenshotMetadata(
            id = file.nameWithoutExtension,
            filename = file.name,
            type = "animated",
            feature = "",
            width = DEFAULT_WIDTH * 2,
            height = 0, // Will be determined by content
            displayWidth = DEFAULT_WIDTH,
            displayHeight = 0,
            theme = "Darcula",
            ide = "IntelliJ IDEA",
            fps = fps,
            durationMs = durationSeconds * 1000
        )
    }
}
