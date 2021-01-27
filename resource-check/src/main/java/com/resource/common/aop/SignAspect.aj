package com.resource.common.aop;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;

@Aspect
@Component//把该文件引入spring容器
public class SignAspect {

//    @Autowired
//    private UserMapper userMapper;
//    private static SignAOP signAOP;
//    @PostConstruct
//    private void init(){
//        signAOP = this;
//        signAOP.userMapper = this.userMapper;
//    }
//    @Autowired
//    private final static Logger logger = LogManager.getLogger("resource");
//
//    @Pointcut("@annotation(com.resource.common.aop.Sign)")
//    public void log() {
//    }
//
//    @Around(value = "log() && @annotation(sign)",argNames = "joinPoint,sign")
//    public Object checkState(ProceedingJoinPoint joinPoint, Sign sign) {
//        String email = JWT.decode(((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("token")).getAudience().get(0);
//        //flag表示该用户是否拥有该权限
//        Boolean flag = false;
//        if ("000000".equals(email)  || signAOP.userMapper.selectByEmailRole(email,"admin")!=null){
//            try {
//                return joinPoint.proceed();
//            } catch (Throwable throwable) {
//                throwable.printStackTrace();
//                return null;
//            }
//        }
//
//        Object[] params = joinPoint.getArgs();
//        JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(params[0]));
//        Integer state = jsonObject.getInteger("state");
//        String currentRole;
//        switch (state){
//            case -1:
//                currentRole = StateEnum.REJECT.getRole();
//                break;
//            case 1:
//                //科室领导审批
//                //根据审批状态判断当前角色
//                currentRole = StateEnum.DIRECTER.getRole();
//                break;
//            case 2:
//                currentRole = StateEnum.LEADER.getRole();
//                break;
//            case 3:
//                currentRole = StateEnum.ADMINISTRATOR.getRole();
//                break;
//            case 4:
//                currentRole = StateEnum.MANAGER.getRole();
//                break;
//            default:
//                throw new SystemException(ResultEnum.ERR.getCode(),"不合法操作");
//        }
//
//        String[] currentRoleArray = currentRole.replace("，", ",").split(",");
//        //根据当前用户和角色名，判断该用户是否分配该角色
//
//        for (int i=0;i<currentRoleArray.length;i++){
//            User user = signAOP.userMapper.selectByEmailRole(email,currentRoleArray[i]);
//            if (user!=null){
//                flag=true;
//                break;
//            }
//        }
//        if (!flag){
//            throw new SystemException(ResultEnum.ERR.getCode(),"无权操作");
//        }else {
//            try {
//                return joinPoint.proceed();
//            } catch (Throwable throwable) {
//                throwable.printStackTrace();
//                return null;
//            }
//
//        }
//    }
}