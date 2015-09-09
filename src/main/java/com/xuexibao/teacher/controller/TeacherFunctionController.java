package com.xuexibao.teacher.controller;

import com.xuexibao.teacher.model.TeacherFunctionPojo;
import com.xuexibao.teacher.service.TeacherFunctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 作者：付国庆
 * 时间：15/4/17－下午2:58
 * 描述：
 */
@Controller
@RequestMapping("teacherFunction")
public class TeacherFunctionController extends AbstractController {

    @Autowired
    private TeacherFunctionService teacherFunctionService;

    @RequestMapping("selectByTeacherId")
    public Object selectByTeacherId() {

        String teacherId = "015280d2-a27e-4e71-b0d1-d5cd4d2c3dcb";
        TeacherFunctionPojo pojo = teacherFunctionService.selectByTeacherId(teacherId);
        return dataJson(pojo);
    }
}
