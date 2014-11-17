#include <stdio.h>
#include <stdlib.h>
#include "projectboarding_SeatingMethod.h"

JNIEXPORT jobject JNICALL Java_projectboarding_SeatingMethod_convert2ArrayToArrayList
(JNIEnv * env, jobject obj, jobjectArray array)
{
    // Get the arraylist class
    jclass ArrayList_class = (*env)->FindClass(env, "java/util/ArrayList");
    
    if (ArrayList_class == 0) {
        fprintf(stderr, "Can't find ArrayList class\n");
        exit(1);
    } else {
        // Get the addAll method for the arrayList
        jmethodID ArrayList_method_addAll = (*env)->GetMethodID(env, ArrayList_class, "addAll", "(Ljava/lang/Collections;)Z");
        
        if (ArrayList_method_addAll == 0) {
            fprintf(stderr, "Can't find ArrayList.addAll method\n");
            exit(1);
        } else {
            
        }
    }
}