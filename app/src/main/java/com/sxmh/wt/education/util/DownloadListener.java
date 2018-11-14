package com.sxmh.wt.education.util;

public interface DownloadListener {
    void startDownload();

    void pauseDownload();

    void finishDownload(String path);

    void downloadProgress(long progress);
}
