package com.example.common;

import com.example.entity.Employee;

/**
 * @create 2022-09-05 20:51
 */
public class BaseContext {
 private static ThreadLocal<Long> threadLocal=new ThreadLocal<>();

 public static void setId(Long id){
  threadLocal.set(id);
 }

 public static Long get(){
  return threadLocal.get();
 }
}
