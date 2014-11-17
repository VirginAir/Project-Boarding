#include <stdio.h>
#include <stdlib.h>
#include "projectboarding_SeatingMethod.h"

/*
 * Class:     projectboarding_SeatingMethod
 * Method:    convertArrayToArrayList
 * Signature: ([[Lprojectboarding/Cell;)Ljava/util/ArrayList;
 */
JNIEXPORT jobject JNICALL Java_projectboarding_SeatingMethod_convertArrayToArrayList
  (JNIEnv *env, jobject obj, jobjectArray objArray)
{
    // Get the arrayList class type
    jclass class_ArrayList = (*env)->FindClass(env, "Ljava/util/ArrayList;");
    if (class_ArrayList == NULL) {
        fprintf(stderr, "Error getting the arrayList type\n");
        exit(1);
    }
    
    // Get the method id to initalize an arrayList
    jmethodID method_init_ArrayList = (*env)->GetMethodID(env, class_ArrayList, "<init>", "()V");
    if (method_init_ArrayList == NULL) {
        fprintf(stderr, "Error getting the arrayList init method\n");
        exit(1);
    }
    
    // Get the add method
    jmethodID method_add_ArrayList = (*env)->GetMethodID(env, class_ArrayList, "add", "(Ljava/lang/Object;)Z");
    if (method_init_ArrayList == NULL) {
        fprintf(stderr, "Error getting the arrayList.addAll(Collection) method\n");
        exit(1);
    }
    
    // Create an arrayList object
    jobject object_ArrayList = (*env)->NewObject(env, class_ArrayList, method_init_ArrayList);
    if (object_ArrayList == NULL) {
        fprintf(stderr, "Error creating the arrayList object\n");
        exit(1);
    }
    
    // Get the arrays class type
    jclass class_Arrays = (*env)->FindClass(env, "java/util/Arrays");
    if (class_Arrays == NULL) {
        fprintf(stderr, "Error getting the arrays class type\n");
        exit(1);
    }
    
    // Get the length of the objArray
    jint objArrayLength = (*env)->GetArrayLength(env, objArray);
    
    // Loop for each
    for (int x = 0; x < objArrayLength; x++) {
        // Get the array object at index x
        jobjectArray object_array = (*env)->GetObjectArrayElement(env, objArray, x);
        if (object_array == NULL) {
            fprintf(stderr, "Error getting the array object\n");
            exit(1);
        }
        
        // Get the length of the array
        jint arrayLength = (*env)->GetArrayLength(env, object_array);
        
        // Loop over each element
        for (int y = 0; y < arrayLength; y++) {
            // Get the object at index y
            jobject object = (*env)->GetObjectArrayElement(env, object_array, y);
            if (object == NULL) {
                fprintf(stderr, "Error getting the object\n");
                exit(1);
            }
            
            // Add the element to the arrayList
            (*env)->CallBooleanMethod(env, object_ArrayList, method_add_ArrayList, object);
        }
    }
    
    return object_ArrayList;
}
