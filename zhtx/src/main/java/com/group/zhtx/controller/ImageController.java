package com.group.zhtx.controller;

import com.group.zhtx.bean.UserPage;
import com.group.zhtx.bean.UserResult;
import com.group.zhtx.message.websocket.client.AdminC;
import com.group.zhtx.message.websocket.service.response.ResponseGroup;
import com.group.zhtx.message.websocket.service.response.ResponseGroupS;
import com.group.zhtx.message.websocket.service.response.ResponseUser;
import com.group.zhtx.message.websocket.service.response.ResponseUserS;
import com.group.zhtx.model.Group;
import com.group.zhtx.model.User;
import com.group.zhtx.repository.GroupRepository;
import com.group.zhtx.repository.UserRepository;
import com.group.zhtx.service.AdministratorService;
import com.group.zhtx.util.common.WebSocketOperateUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ImageController {
    @Resource
    private AdministratorService service;
    @Resource
    private UserRepository userRepository;
    @Resource
    private GroupRepository groupRepository;

    @RequestMapping("/userPortrait/{name}")
    public void getImage(@PathVariable String name, HttpServletResponse response, HttpSession session) throws Exception {
        System.out.println("客户端传过来的参数：" + name);
        File file = new File("E:\\numberThree\\up_semester\\sx\\androidweb\\zhtx\\src\\main\\resources\\static\\userPortrait");
        File imageFile = new File(file.getAbsolutePath(), name);
        FileInputStream inputStream;
        if (imageFile.exists()) {
            inputStream = new FileInputStream(imageFile);
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes, 0, bytes.length);
            response.getOutputStream().write(bytes);
        } else {
            File defaultFile = new File(file.getAbsolutePath(), "12345678.png");
            inputStream = new FileInputStream(defaultFile);
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes, 0, bytes.length);
            response.getOutputStream().write(bytes);
        }
    }

    @RequestMapping("/paging")
    public String paging() {
        return "kkk";
    }

    @RequestMapping(value = "/getData")
    public Map loadData(int pageIndex,int status) throws Exception {//获取开启或禁用用户的信息
        ResponseUserS responseUserS = new ResponseUserS();
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        Sort sort=new Sort(Sort.Direction.DESC,"uuid");
        Specification <User>specification=new Specification<User>() {
            @Override
            public Predicate toPredicate(Root <User>root, CriteriaQuery <?>criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicatesList = new ArrayList<>();
                predicatesList.add(criteriaBuilder.equal(root.get("status").as(String.class),status));
                Predicate []p=new Predicate[predicatesList.size()];
                return criteriaBuilder.and(predicatesList.toArray(p));
            }
        };
        Pageable pageable= PageRequest.of(pageIndex-1,2,sort);
        Page<User> userPage=userRepository.findAll(specification,pageable);
        List<User> users = userPage.getContent();
        UserPage page=new UserPage();
        if(users.size()==0){
            page.setCount(2);//如果没有用户时，设置默认的
            page.setCurrentPage(0);
            page.setTotalPage(0);
            page.setTotalCount(0);
            map.put("page",page);
            map.put("users", responseUserS);
            return map;
        }else{
            page.setCount(2);//设置一页5条数据
            page.setCurrentPage(pageIndex);//设置当前页
            page.setTotalPage(userPage.getTotalPages());//设置总页数
            page.setTotalCount(userPage.getTotalElements());//设置总记录数
            System.out.println("用户数量：" + users.size());
            System.out.println("总数量数："+userPage.getTotalElements());
            for (int i = 0; i < users.size(); i++) {
                User user = users.get(i);
                if (user != null) {
                    ResponseUser responseUser = new ResponseUser();
                    if (user.getPortrait() == null || user.getPortrait().equals("")) {
                        responseUser.setUrl(WebSocketOperateUtil.Portrait_Image);
                    } else {
                        responseUser.setUrl(WebSocketOperateUtil.Portrait_Url + user.getPortrait() + WebSocketOperateUtil.Portrait_Suffix);
                    }
                    responseUser.setUserId(user.getUuid());
                    responseUser.setUserName(user.getName());
                    responseUser.setStatus(user.getStatus());
                    //responseUsers.add(responseUser);
                    responseUserS.addResponseUser(responseUser);
                    page.add(responseUser);
                    //System.out.println("添加用户："+responseUser.getUserName());
                }
            }
            map.put("users", responseUserS);
            map.put("page",page);
            return map;
        }
    }
    @RequestMapping("/pager")
    public List<User>sortPageUser(int pageIndex){
        System.out.println("pageIndex="+pageIndex);
        Sort sort=new Sort(Sort.Direction.DESC,"uuid");
        Pageable page= PageRequest.of(pageIndex-1,5,sort);
        Page<User> userPage=userRepository.findAll(page);
        List<User>users=userPage.getContent();
        System.out.println("当前页："+userPage.getNumber()+1);
        System.out.println("总页数："+userPage.getTotalPages());
        System.out.println("总记录数："+userPage.getTotalElements());
        System.out.println("查询当前页面记录数："+userPage.getNumberOfElements());
        return users;
    }

    @RequestMapping("/update")
    public Map forbidAndStart(@RequestBody UserResult result){
        System.out.println("前端传来的结果："+result.getResult());
        System.out.println("前端传来的用户id："+result.getUuid());
        System.out.println("前端传来的类型："+result.getType());
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        if(result.getType().equals("user")){
            User user=userRepository.findByUuid(result.getUuid());
            if(user==null){
                map.put("code",0);
                map.put("status","fail");
                return map;
            }
            user.setStatus(result.getResult());
            map.put("code",1);
            map.put("status","success");
            userRepository.saveAndFlush(user);
        }else if(result.getType().equals("group")){
            Group group=groupRepository.findByUuid(result.getUuid());
            if(group==null){
                map.put("code",0);
                map.put("status","fail");
                return map;
            }
            group.setStatus(result.getResult());
            map.put("code",1);
            map.put("status","success");
            groupRepository.saveAndFlush(group);
        }
        return map;
    }
    @RequestMapping("/getGroupData")
    public Map loadGroupData(int pageIndex,int status){
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        ResponseGroupS responseGroupS=new ResponseGroupS();
        Sort sort=new Sort(Sort.Direction.DESC,"uuid");
        Specification<Group>specification=new Specification<Group>() {
            @Override
            public Predicate toPredicate(Root<Group> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate>predicateList=new ArrayList<>();
                predicateList.add(criteriaBuilder.equal(root.get("status").as(String.class),status));
                Predicate []p=new Predicate[predicateList.size()];
                return criteriaBuilder.and(predicateList.toArray(p));
            }
        };
        Pageable pageable=PageRequest.of(pageIndex-1,3,sort);
        Page<Group>groupPage=groupRepository.findAll(specification,pageable);
        List<Group>groups=groupPage.getContent();
        UserPage page=new UserPage();
        if(groups.size()==0){
            page.setCount(3);
            page.setTotalCount(0);
            page.setCurrentPage(0);
            page.setTotalPage(0);
            map.put("page",page);
            map.put("groups",responseGroupS);
            return map;
        }else{
            page.setCount(3);
            page.setCurrentPage(pageIndex);
            page.setTotalCount(groupPage.getTotalElements());
            page.setTotalPage(groupPage.getTotalPages());
            System.out.println("群数量："+groups.size());
            System.out.println("总群数量："+groupPage.getTotalElements());
            for(int i=0;i<groups.size();i++){
                Group group=groups.get(i);
                if(group!=null){
                    ResponseGroup responseGroup=new ResponseGroup();
                    if(group.getPortarit()==null||group.getPortarit().equals("")){
                        responseGroup.setUrl(WebSocketOperateUtil.Portrait_Image_Group);
                    }else{
                        responseGroup.setUrl(WebSocketOperateUtil.Portrait_Url+group.getPortarit()+WebSocketOperateUtil.Portrait_Suffix);
                    }
                    responseGroup.setGroupId(group.getUuid());
                    responseGroup.setGroupName(group.getName());
                    responseGroup.setStatus(group.getStatus());
                    responseGroupS.addResponseGroup(responseGroup);
                    page.add(responseGroup);
                }
            }
            map.put("page",page);
            map.put("groups",responseGroupS);
            return map;
        }
    }
    @RequestMapping("/searchByWord")
    public Map searchByKeyWord(String key){
        Map<String,Object>map=new LinkedHashMap<>();
        List<User> userList=service.findByKeyWord(key);
        System.out.println(userList.size());
        ResponseUserS responseUserS=new ResponseUserS();
        if(userList.size()==0){
            map.put("users",responseUserS);
            map.put("code",0);
            return map;
        }
        for(int i=0;i<userList.size();i++){
            User user=userList.get(i);
            ResponseUser responseUser=new ResponseUser();
            responseUser.setStatus(user.getStatus());
            responseUser.setUserName(user.getName());
            responseUser.setUserId(user.getUuid());
            if(user.getPortrait()==null||user.getPortrait().equals("")){
                responseUser.setUrl(WebSocketOperateUtil.Portrait_Image);
            }else{
                responseUser.setUrl(WebSocketOperateUtil.Portrait_Url+user.getPortrait()+WebSocketOperateUtil.Portrait_Suffix);
            }
            responseUserS.addResponseUser(responseUser);
        }
        map.put("users",responseUserS);
        map.put("code",1);
        return map;
    }
}
