package com.sxmh.wt.education.fragment.live;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.sxmh.wt.education.MyApplication;
import com.sxmh.wt.education.activity.live.RealLiveActivity;
import com.sxmh.wt.education.adapter.RvChatAdapter;
import com.sxmh.wt.education.base.BaseFragment;
import com.sxmh.wt.education.model.ChatMessage;
import com.sxmh.wt.education.model.User;
import com.sxmh.wt.education.model.response.FileUploadResponse;
import com.sxmh.wt.education.model.response.live.LiveRoomInfoResponse;
import com.sxmh.wt.education.util.BitMapUtils;
import com.sxmh.wt.education.util.Constant;
import com.sxmh.wt.education.util.FileUtils;
import com.sxmh.wt.education.util.NUtil;
import com.sxmh.wt.education.util.Net;
import com.sxmh.wt.education.util.PhotoDialog;
import com.sxmh.wt.education.util.ToastUtil;
import com.sxmh.wt.education.view.MyRecyclerView;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;

import com.sxmh.wt.education.R;

import static android.app.Activity.RESULT_OK;

public class ChatFragment extends BaseFragment {
    @InjectView(R.id.rv_chat)
    MyRecyclerView rvChat;
    @InjectView(R.id.iv_send)
    ImageView ivSend;
    @InjectView(R.id.et_message)
    EditText etMessage;
    @InjectView(R.id.iv_send_image)
    ImageView ivSendImage;

    private static final String TAG = "ChatFragment";
    public static final String MSG_TEXT = "text";
    public static final String MSG_IMAGE = "image";
    public static final int REFRESH_LIST = 1;

    private List<ChatMessage> list;
    private RvChatAdapter rvChatAdapter;
    private WebSocketClient mSocketClient;
    private String msgSend;

    private static final int ALBUM = 919;
    private static final int TAKE_PHOTO = 918;
    private File outputImage;

    @Override
    protected int initLayoutId() {
        return R.layout.fragment_chat;
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case REFRESH_LIST:
                    rvChatAdapter.notifyDataSetChanged();
                    rvChat.scrollToPosition(list.size() - 1);
                    etMessage.setText("");
                    break;
            }
        }
    };

    @Override
    protected void initData() {
        Bundle arguments = getArguments();
        LiveRoomInfoResponse infoResponse = (LiveRoomInfoResponse) arguments.getSerializable(RealLiveActivity.LIVE_ROOM_INFO_RESPONSE);

        list = new ArrayList<>();
        rvChatAdapter = new RvChatAdapter(getContext(), list);
        rvChat.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rvChat.setAdapter(rvChatAdapter);

        new Thread(() -> {
            try {
                String userName = User.getInstance().getUserName();
                String address = infoResponse.getChatUrl() + "/" + userName;
                mSocketClient = new WebSocketClient(new URI(address), new Draft_6455()) {
                    @Override
                    public void onOpen(ServerHandshake handshakedata) {
                        Log.e(TAG, "打开通道" + handshakedata.getHttpStatus());
                    }

                    @Override
                    public void onMessage(String message) {
                        Log.e(TAG, "接收消息" + message);
                        parseMessage(message);
                    }

                    @Override
                    public void onClose(int code, String reason, boolean remote) {
                        Log.e(TAG, "通道关闭 code" + code + "  reason " + reason);
                    }

                    @Override
                    public void onError(Exception ex) {
                        ex.printStackTrace();
                        Log.e(TAG, "链接错误");
                    }
                };
                mSocketClient.connect();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void parseMessage(String message) {
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(message);
            String user = jsonObject.getString("user");
            String time = jsonObject.getString("time");
            JSONArray chat = jsonObject.getJSONArray("chat");
            JSONObject contentObject = (JSONObject) chat.get(0);
            String type = contentObject.getString("type");
            String content = contentObject.getString("content");

            addNewMsg(user, time, false, type, content);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateContent(int request, Object content) {
        if (request == Net.REQUEST_UPLOAD_IMAGE) {
            FileUploadResponse response = (FileUploadResponse) content;
            sendMsg(MSG_IMAGE, response.getUrl());
        }
    }

    @OnClick({R.id.iv_send, R.id.iv_send_image})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_send:
                sendMsg(MSG_TEXT, etMessage.getText().toString());
                break;
            case R.id.iv_send_image:
                new PhotoDialog(getActivity(), R.style.defaultDialogStyle, new PhotoDialog.OnClickListener() {
                    @Override
                    public void onPhotoClick() {
                        fromPhoto(TAKE_PHOTO);
                    }

                    @Override
                    public void onGalleryClick() {
                        fromAlbum(ALBUM);
                    }
                }).show();
                break;
        }
    }

    private void fromAlbum(int flag) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        // 开启一个带有返回值的Activity，请求码为ALBUM
        startActivityForResult(intent, flag);
    }

    private void fromPhoto(int flag) {
        outputImage = new File(Constant.CACHE_PATH, FileUtils.generateImageName());
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Uri imageUri;
        if (Build.VERSION.SDK_INT < 24) {
            imageUri = Uri.fromFile(outputImage);
        } else {
            imageUri = FileProvider.getUriForFile(MyApplication.getInstance(), "com.sxmh.wt.education", outputImage);
        }
        // 启动相机程序
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, flag);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    String imgPath = outputImage.getAbsolutePath();
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 4;
                    Bitmap bitmap = BitmapFactory.decodeFile(imgPath, options);
                    NUtil.saveBitmapFile(bitmap, imgPath);
                    net.upLoadImage(imgPath);
                }
                break;
            case ALBUM:
                if (resultCode == RESULT_OK) {
                    // 从相册返回的数据
                    if (data != null) {
                        // 得到图片的全路径
                        Uri uri = data.getData();
                        String imagePath = BitMapUtils.getRealPathFromUri(getActivity(), uri);

                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inSampleSize = 4;
                        Bitmap bitmap = BitmapFactory.decodeFile(imagePath, options);
                        String path = Constant.CACHE_PATH + "/" + imagePath.substring(imagePath.lastIndexOf("/"));
                        NUtil.saveBitmapFile(bitmap, path);
                        net.upLoadImage(path);
                    }
                }
                break;
        }
    }

    private void sendMsg(String type, String content) {
        if (TextUtils.isEmpty(content)) {
            ToastUtil.newToast(getContext(), "不能发送空消息");
            return;
        }
        if (mSocketClient.isOpen()) {
            String time = NUtil.getFormatDate(System.currentTimeMillis());
            User instance = User.getInstance();
            msgSend = "{\"type\":\"chat\",\"user\":\"" + instance.getUserName() + "\",\"time\":\"" + time + "\",\"chat\":[{\"type\":\"" + type + "\",\"content\":\"" + content + "\"}]}";
            mSocketClient.send(msgSend);
            addNewMsg(instance.getUserName(), time, true, type, content);
        }
    }

    /**
     * 将一条消息添加到显示区域
     */
    private void addNewMsg(String name, String time, boolean isMySend, String type, String content) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setUser(name);
        chatMessage.setTime(time);
        chatMessage.setMySend(isMySend);
        chatMessage.setType("");
        ArrayList<ChatMessage.ChatBean> chatList = new ArrayList<>();
        ChatMessage.ChatBean chatBean = new ChatMessage.ChatBean();
        chatBean.setType(type);
        chatBean.setContent(content);
        chatList.add(chatBean);
        chatMessage.setChat(chatList);
        list.add(chatMessage);
        handler.sendEmptyMessage(REFRESH_LIST);
    }
}
