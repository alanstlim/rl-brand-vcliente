package com.example.rlbrand.helper;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class Permission {

    public static boolean validatePermission(String[] permission, Activity activity, int requestCode){

        if (Build.VERSION.SDK_INT >= 23) {

            List<String> permissionList = new ArrayList<>();

            //Verificar se as permissões foram liberadas
            for (String readPermission : permission){
               Boolean havePermission = ContextCompat.checkSelfPermission(activity, readPermission) == PackageManager.PERMISSION_GRANTED;
               if ( !havePermission ) permissionList.add(readPermission);
            }

            // Se a lista estiver vazia não precisa solicitar
            if ( permissionList.isEmpty()) return true;

            String [] newPermissions = new String[ permissionList.size() ];
            permissionList.toArray( newPermissions );

            //Solicitar permissão
            ActivityCompat.requestPermissions(activity, newPermissions, requestCode);


        }

        return true;
    }
}
