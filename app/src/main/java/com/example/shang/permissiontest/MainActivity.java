package com.example.shang.permissiontest;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // 打电话按钮
    public void call(View view) {
//        // 检测是否拥有打电话权限
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//            // 申请权限
 //          ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
//        } else {
//            // 拨打电话
//            doCallPhone();
//        }
        PermissionGen.needPermission(this,1,new String[]{Manifest.permission.CALL_PHONE});
    }

    @PermissionSuccess(requestCode = 1)
    private void doCallPhone() {
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + "10086");
        intent.setData(data);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        startActivity(intent);
    }

    //下载网络文件按钮
    public void down(View view){
//        if (ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
//            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},2);
//        }else {
//            doDownLoad();
//        }
        PermissionGen.needPermission(this,2,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE});
    }

    //http://oqcummb0e.bkt.clouddn.com/bizhi_1.jpg

    @PermissionSuccess(requestCode = 2)
    private void doDownLoad() {
        new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL("http://oqcummb0e.bkt.clouddn.com/bizhi_2.jpg");
                    InputStream is = url.openStream();
                    OutputStream os = openFileOutput("tupian.jpg",MODE_PRIVATE);
                    byte[]buf = new byte[1024];
                    int len = 0;
                    while ((len = is.read())>0){
                        os.write(buf,0,len);
                    }
                    is.close();
                    os.close();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
        Toast.makeText(MainActivity.this,"下载文件到本地成功！",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        switch (requestCode){
//            case 1:
//                if (grantResults[0]==PackageManager.PERMISSION_GRANTED){
//                    doCallPhone();
//                }else {
//                    Toast.makeText(MainActivity.this,"打电话权限未被授予",Toast.LENGTH_SHORT).show();
//                }
//                break;
//            case 2:
//                if (grantResults[0]==PackageManager.PERMISSION_GRANTED){
//                    doDownLoad();
//                }else {
//                    Toast.makeText(MainActivity.this,"读写SD卡权限未被授予",Toast.LENGTH_SHORT).show();
//                }
//                break;
//        }
        PermissionGen.onRequestPermissionsResult(this,requestCode,permissions,grantResults);

    }
}
