package com.github.xielong.aihub.adapter;

public interface AIModelInvoker {
    String invoke(String model, String input) throws Exception;
}
