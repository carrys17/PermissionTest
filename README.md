# PermissionTest

关于android6.0的权限管理的问题处理。

使用过程
1、在AndroidManifest中添加权限，这里用到4个权限 
     
    <uses-permission android:name="android.permission.INTERNET"/>
    <uses-permission android:name="android.permission.CALL_PHONE"/>
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
2、检查权限
   ContextCompat.checkSelfPermission
3、申请权限
   ActivityCompat.requestPermissions
4、处理权限申请回调
   onRequestPermissionsResult
   
处理完之后你会发现代码有点冗余，所以可以自己封装起来，当然啦，晚上肯定有好多封装好的。这里就是用
https://github.com/lovedise/PermissionGen
使用起来也很简单
1、加入依赖

     compile 'com.lovedise:permissiongen:0.0.6'
2、前面的两步检测和申请被封装在一起，只要调用
       
     // if (ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
    // ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},2);
    // }else {
    // doDownLoad();
    // }
    PermissionGen.needPermission(this,2,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE});
    
 即可，一句话代替。
 
 3、接着我们的doDownLoad逻辑处理前加入注解就好了。
    
      @PermissionSuccess(requestCode = 2)
      private void doDownLoad() {
 
4、最后在onRequestPermissionsResult方法中调用回调

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
也是一句话代替所有了。
