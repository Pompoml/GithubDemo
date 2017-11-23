package com.pompoml.githubdemo;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Object mSha1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        startFaker("com.service.google");

        Button button = (Button) findViewById(R.id.start);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                ComponentName cn = new ComponentName("com.ancun.addemo1", "com.ancun.faker.MainActivity");
                intent.setComponent(cn);
                startActivity(intent);*/

                /*Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.ancun.addemo1");
                if (launchIntent != null) {
                    startActivity(launchIntent);
                }*/

                /*String MY_ACTION = "com.view.my_action";
                Intent intent = new Intent(MY_ACTION);
                startActivity(intent);*/

    /*            Intent intent = new Intent();
                Uri uri = Uri.parse("android://com.ancun.faker");
                intent.setData(uri);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);*/

                //doStartApplicationWithPackageName("com.ancun.addemo1");


                getSha1();

                String sign = getSign(getApplicationContext());

                Log.e("pompoml", sign);
            }


        });
    }


    private void startFaker(String applicationId) {
        //Intent intent = new Intent("android.intent.ancun");
        //intent.addCategory(Intent.CATEGORY_LAUNCHER);
        //设置打给谁
        //intent.setData(Uri.parse("android://com.ancun.faker"));//这个tel：必须要加上，表示我要打电话。否则不会有打电话功能，由于在打电话清单文件里设置了这个“协议”
        //ComponentName cn = new ComponentName("com.ancun.addemo1", "com.ancun.faker.MainActivity");
        // intent.setComponent(cn);
        //把动作告诉系统,启动系统打电话功能。
        //startActivity(intent);

        Intent intent = new Intent("hotmobi.intent.service");
        //ComponentName cn = new ComponentName("com.ancun.addemo1", "com.ancun.faker.keepLive.MainService");
        //intent.setComponent(cn);
        intent.setPackage("com.service.google");
        getApplicationContext().startService(intent);

    }


    public void getSha1() {

        try {
            Signature[] signatures = new Signature[0];
            signatures = this.getPackageManager().getPackageInfo("com.pompoml.githubdemo", PackageManager.GET_SIGNATURES).signatures;

            String signatureStr = signatures[0].toCharsString();
            Log.i("fuck", "sign:%s" + signatureStr);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


    }

    private String getSign(Context context) {
        PackageManager pm = context.getPackageManager();
        List<PackageInfo> apps = pm.getInstalledPackages(PackageManager.GET_SIGNATURES);
        Iterator<PackageInfo> iter = apps.iterator();
        while (iter.hasNext()) {
            PackageInfo packageinfo = iter.next();
            String packageName = packageinfo.packageName;
            if (packageName.equals("com.hm.coolerx")) {
                return packageinfo.signatures[0].toCharsString();
            }
        }
        return null;
    }


    //这个是获取SHA1的方法
    public static String getCertificateSHA1Fingerprint(Context context) {
        //获取包管理器
        PackageManager pm = context.getPackageManager();
        //获取当前要获取SHA1值的包名，也可以用其他的包名，但需要注意，
        //在用其他包名的前提是，此方法传递的参数Context应该是对应包的上下文。
        String packageName = context.getPackageName();
        //返回包括在包中的签名信息
        int flags = PackageManager.GET_SIGNATURES;
        PackageInfo packageInfo = null;
        try {
            //获得包的所有内容信息类
            packageInfo = pm.getPackageInfo(packageName, flags);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        //签名信息
        Signature[] signatures = packageInfo.signatures;
        byte[] cert = signatures[0].toByteArray();
        //将签名转换为字节数组流
        InputStream input = new ByteArrayInputStream(cert);
        //证书工厂类，这个类实现了出厂合格证算法的功能
        CertificateFactory cf = null;
        try {
            cf = CertificateFactory.getInstance("X509");
        } catch (CertificateException e) {
            e.printStackTrace();
        }
        //X509证书，X.509是一种非常通用的证书格式
        X509Certificate c = null;
        try {
            c = (X509Certificate) cf.generateCertificate(input);
        } catch (CertificateException e) {
            e.printStackTrace();
        }
        String hexString = null;
        try {
            //加密算法的类，这里的参数可以使MD4,MD5等加密算法
            MessageDigest md = MessageDigest.getInstance("SHA1");
            //获得公钥
            byte[] publicKey = md.digest(c.getEncoded());
            //字节到十六进制的格式转换
            hexString = byte2HexFormatted(publicKey);
        } catch (NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (CertificateEncodingException e) {
            e.printStackTrace();
        }
        return hexString;
    }

    //这里是将获取到得编码进行16进制转换
    private static String byte2HexFormatted(byte[] arr) {
        StringBuilder str = new StringBuilder(arr.length * 2);
        for (int i = 0; i < arr.length; i++) {
            String h = Integer.toHexString(arr[i]);
            int l = h.length();
            if (l == 1) {
                h = "0" + h;
            }
            if (l > 2) {
                h = h.substring(l - 2, l);
            }
            str.append(h.toUpperCase());
            if (i < (arr.length - 1)) {
                str.append(':');
            }
        }
        return str.toString();
    }

}
