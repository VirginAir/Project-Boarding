#include <stdlib.h>
#include <stdio.h>
#include "projectboarding_PlaneDimension.h"

/*
 * Class:     projectboarding_PlaneDimension
 * Method:    getAllSeats
 * Signature: ()[[Lprojectboarding/Cell;
 */
JNIEXPORT jobjectArray JNICALL Java_projectboarding_PlaneDimension_getAllSeats
(JNIEnv *env, jobject obj)
{
    // Get a reference to the object class
    jclass class = (*env)->GetObjectClass(env, obj);
    if (class == NULL) {
        fprintf(stderr, "Error getting the class");
        exit(1);
    }
    
    // Get the field id
    jfieldID fieldID = (*env)->GetFieldID(env, class, "planeSeats", "[[Lprojectboarding/Cell;");
    if (fieldID == NULL) {
        fprintf(stderr, "Error getting the field id for 'planeSeats'");
        exit(1);
    }
    
    // Get the object array
    jobjectArray objArray = (*env)->GetObjectField(env, obj, fieldID);
    if (objArray == NULL) {
        fprintf(stderr, "Error getting the field");
        exit(1);
    } else {
        return objArray;
    }
}

/*
 * Class:     projectboarding_PlaneDimension
 * Method:    getPrioritySeats
 * Signature: ()[[Lprojectboarding/Cell;
 */
JNIEXPORT jobjectArray JNICALL Java_projectboarding_PlaneDimension_getPrioritySeats
(JNIEnv *env, jobject obj)
{
    // Get a reference to the object class
    jclass class = (*env)->GetObjectClass(env, obj);
    if (class == NULL) {
        fprintf(stderr, "Error getting the class");
        exit(1);
    }
    
    // Get the field id
    jfieldID fieldID = (*env)->GetFieldID(env, class, "prioritySeats", "[[Lprojectboarding/Cell;");
    if (fieldID == NULL) {
        fprintf(stderr, "Error getting the field id for 'prioritySeats'");
        exit(1);
    }
    
    // Get the object array
    jobjectArray objArray = (*env)->GetObjectField(env, obj, fieldID);
    if (objArray == NULL) {
        fprintf(stderr, "Error getting the field");
        exit(1);
    } else {
        return objArray;
    }
}

/*
 * Class:     projectboarding_PlaneDimension
 * Method:    getNormalSeats
 * Signature: ()[[Lprojectboarding/Cell;
 */
JNIEXPORT jobjectArray JNICALL Java_projectboarding_PlaneDimension_getNormalSeats
(JNIEnv *env, jobject obj)
{
    // Get a reference to the object class
    jclass class = (*env)->GetObjectClass(env, obj);
    if (class == NULL) {
        fprintf(stderr, "Error getting the class");
        exit(1);
    }
    
    // Get the field id
    jfieldID fieldID = (*env)->GetFieldID(env, class, "normalSeats", "[[Lprojectboarding/Cell;");
    if (fieldID == NULL) {
        fprintf(stderr, "Error getting the field id for 'normalSeats'");
        exit(1);
    }
    
    // Get the object array
    jobjectArray objArray = (*env)->GetObjectField(env, obj, fieldID);
    if (objArray == NULL) {
        fprintf(stderr, "Error getting the field");
        exit(1);
    } else {
        return objArray;
    }
}

/*
 * Class:     projectboarding_PlaneDimension
 * Method:    getNumberOfPriorityRows
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_projectboarding_PlaneDimension_getNumberOfPriorityRows
(JNIEnv *env, jobject obj)
{
    jobjectArray objArray = Java_projectboarding_PlaneDimension_getPrioritySeats(env, obj);
    
    return (*env)->GetArrayLength(env, objArray);
}

/*
 * Class:     projectboarding_PlaneDimension
 * Method:    getNumberOfNormalRows
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_projectboarding_PlaneDimension_getNumberOfNormalRows
(JNIEnv *env, jobject obj)
{
    jobjectArray objArray = Java_projectboarding_PlaneDimension_getNormalSeats(env, obj);
    
    return (*env)->GetArrayLength(env, objArray);
}
/*
 * Class:     projectboarding_PlaneDimension
 * Method:    totalNumberOfRows
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_projectboarding_PlaneDimension_totalNumberOfRows
(JNIEnv *env, jobject obj)
{
    jobjectArray objArray = Java_projectboarding_PlaneDimension_getAllSeats(env, obj);
    
    return (*env)->GetArrayLength(env, objArray);
}
