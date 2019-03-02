package com.example.web.controller;

import com.example.web.model.Person;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LuTshoes on 2017/7/16 0016.
 * lutshoes@163.com
 */
@Controller
public class IndexController {

    @RequestMapping("/index")
    public  String index(Model model){
        Person p=new Person("lujia",20);

        List<Person> persons=new ArrayList<>();
        persons.add(new Person("詹姆斯",10));
        persons.add(new Person("安东尼",11));
        persons.add(new Person("韦德",12));
        persons.add(new Person("保罗",12));
        persons.add(new Person("哈登",12));
        model.addAttribute("personSingle",p);
        model.addAttribute("persons",persons);
        return  "index";
    }
}
