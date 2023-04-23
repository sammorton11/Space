package com.samm.space

import android.app.DownloadManager
import android.content.Context
import android.os.Environment
import androidx.test.core.app.ApplicationProvider
import com.samm.space.presentation_common.util.FileHandler
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test

/**
 *  TODO: NEEDS TO BE MOVED TO ANDROID TEST PACKAGE - MUST RUN ON DEVICE
 */
class FileHandlerTest {

    private lateinit var context: Context
    private lateinit var downloadManager: DownloadManager
    private lateinit var fileHandler: FileHandler

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        downloadManager = mockk(relaxed = true)
        fileHandler = FileHandler()
    }

    @Test
    fun `downloadFile should enqueue download request with correct parameters`() {
        val url = "http://example.com"
        val fileName = "file.mp4"
        val mimeType = "video/mp4"
        val subPath = "downloads/"
        val request = mockk<DownloadManager.Request>()

        every { request.setTitle(any()) } returns request
        every { request.setMimeType(any()) } returns request
        every { request.setDescription(any()) } returns request
        every { request.setNotificationVisibility(any()) } returns request
        every { request.setDestinationInExternalPublicDir(any(), any()) } returns request
        every { request.setAllowedOverMetered(any()) } returns request
        every { request.setAllowedOverRoaming(any()) } returns request

        every {
            downloadManager.enqueue(request)
        } returns 12345L // Replace with any valid download ID

        every {
            context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        } returns downloadManager

        fileHandler.downloadFile(context, url, fileName, mimeType, subPath)

        verify {
            request.setTitle(fileName)
            request.setMimeType(mimeType)
            request.setDescription("Downloading...")
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
            request.setDestinationInExternalPublicDir(
                Environment.DIRECTORY_DOWNLOADS,
                subPath + fileName
            )
            request.setAllowedOverMetered(true)
            request.setAllowedOverRoaming(true)

            downloadManager.enqueue(request)
        }
    }
}
