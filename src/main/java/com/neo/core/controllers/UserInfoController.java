package com.neo.core.controllers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.neo.core.dto.*;
import com.neo.core.entities.PasswordPolicies;
import com.neo.core.service.PasswordPoliciesService;
import io.swagger.models.auth.In;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.*;

import com.neo.core.constants.ResponseFontendDefine;
import com.neo.core.entities.UserInfo;
import com.neo.core.security.SecurityCredentialsConfig;
import com.neo.core.service.UserInfoService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("user-info")
@Slf4j
@CrossOrigin("*") //
public class UserInfoController extends BaseController {
    private final String START_LOG = "UserInfo {}";
    private final String END_LOG = "UserInfo {} finished in: {}";

    @Autowired
    UserInfoService service;

    @Autowired
    SecurityCredentialsConfig config;

    @Autowired
    PasswordPoliciesService passwordPoliciesService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

   // done
    @GetMapping()
    public ResponseModel doSearch(@RequestParam(value = "username", required = false) String username,
                                  @RequestParam(value = "email", required = false) String email,
                                  @RequestParam(value = "fullName", required = false) String fullName,
                                  @RequestParam(value = "phone", required = false) String phone,
                                  @RequestParam(value = "statusOnline", required = false) List<Integer> statusOnline,
                                  @RequestParam(value = "scoreFrom", required = false) Integer scoreFrom,
                                  @RequestParam(value = "scoreTo", required = false) Integer scoreTo,
                                  @RequestParam(value = "fromDate", required = false) String fromDate,
                                  @RequestParam(value = "toDate", required = false) String toDate,
                                  @RequestParam(defaultValue = "0") int currentPage,
                                  @RequestParam(defaultValue = "10") int perPage,
                                  HttpServletRequest request) {
        final String action = "doSearch";
        StopWatch sw = new StopWatch();
        sw.start();
        log.info(START_LOG, action);
        try {
            Pageable paging = PageRequest.of(currentPage, perPage);
            Page<UserInfoDTO> pageResult = null;

            if (fromDate != null && fromDate.trim().isEmpty()) {
                fromDate = null;
            }
            if (toDate != null && toDate.trim().isEmpty()) {
                toDate = null;
            }
            if (username != null && username.trim().isEmpty()) {
                username = null;
            }
            if (phone != null && phone.trim().isEmpty()) {
                phone = null;
            }
            if (email != null && email.trim().isEmpty()) {
                email = null;
            }

            if (fullName != null && fullName.trim().isEmpty()) {
                fullName = null;
            }

            pageResult = service.doSearch(username,email,fullName,phone,statusOnline,scoreFrom,scoreTo,fromDate,toDate, getUserFromToken(request),paging);
            if ((pageResult == null || pageResult.isEmpty())) {
                ResponseModel responseModel = new ResponseModel();
                responseModel.setErrorMessages("Không tìm thấy người dùng.");
                responseModel.setStatusCode(HttpStatus.SC_OK);
                responseModel.setCode(ResponseFontendDefine.CODE_NOT_FOUND);
                return responseModel;
            }

            PagingResponse<UserInfoDTO> result = new PagingResponse<>();
            result.setTotal(pageResult.getTotalElements());
            result.setItems(pageResult.getContent());

            ResponseModel responseModel = new ResponseModel();
            responseModel.setContent(result);
            responseModel.setStatusCode(HttpStatus.SC_OK);
            responseModel.setCode(ResponseFontendDefine.CODE_SUCCESS);
            return responseModel;
        } catch (Exception e) {
            throw handleException(e);
        } finally {
            sw.stop();
            log.info(END_LOG, action, sw.getTotalTimeSeconds());
        }
    }
    




    //done
    @PostMapping()
    public ResponseModel doCreate(@RequestBody UserInfo entity, HttpServletRequest request) {
        final String action = "doCreate";
        StopWatch sw = new StopWatch();
        log.info(START_LOG, action);
        LocalDateTime currentTime = LocalDateTime.now();
        try {
            String customUserName = entity.getUsername().trim();
            String customPassword = entity.getPassword().trim();
            String customFullName = entity.getFullName().trim();
            LocalDateTime customDOB = entity.getDob();
            Integer customGender = entity.getGender();
            Integer customRole = entity.getRoles();
            String customEmail = entity.getEmail().trim();
            String customPhone = entity.getPhone().trim();
            String customAddress = entity.getAddress().trim();
            Integer customIdDoctor = entity.getIdDoctor();


            //check tên đăng nhập đã tồn tại
            UserInfo checkExisted = service.findByUsername(customUserName);
            if (checkExisted != null && checkExisted.getStatus() != 2 ) {
                // username đã tồn tại
                ResponseModel responseModel = new ResponseModel();
                responseModel.setStatusCode(HttpStatus.SC_OK);
                responseModel.setCode(ResponseFontendDefine.CODE_ALREADY_EXIST);
                responseModel.setErrorMessages("Người dùng đã tồn tại trên hệ thống.");
                return responseModel;
            }
            //check email đã tồn tại
            UserInfo checkEmailExisted = service.findByEmail(customEmail);
            if (checkEmailExisted != null && checkEmailExisted.getStatus() != 2 && checkEmailExisted.getEmail() != null) {
                ResponseModel responseModel = new ResponseModel();
                responseModel.setStatusCode(HttpStatus.SC_OK);
                responseModel.setCode(ResponseFontendDefine.CODE_GMAIL_EXIST);
                responseModel.setErrorMessages("Email đã tồn tại trên hệ thống.");
                return responseModel;
            }
            entity.setUsername(customUserName);
            // ma hoa mk
            String password = config.passwordEncoder().encode(customPassword);
            entity.setPassword(password);
            entity.setFullName(customFullName);
            entity.setDob(customDOB);
            entity.setGender(customGender);
            entity.setRoles(customRole);
            entity.setEmail(customEmail);
            entity.setPhone(customPhone);
            entity.setAddress(customAddress);
            entity.setIdDoctor(customIdDoctor);
            entity.setStatus(1);
            entity.setStatusOnline(2);
            entity.setCreatedDate(currentTime);
            service.create(entity);


            ResponseModel responseModel = new ResponseModel();
            responseModel.setStatusCode(HttpStatus.SC_OK);
            responseModel.setCode(ResponseFontendDefine.CODE_SUCCESS);
            return responseModel;
        } catch (Exception e) {
            throw handleException(e);
        } finally {
            log.info(END_LOG, action, sw.getTotalTimeSeconds());
        }
    }


//done
    @PutMapping()
    public ResponseModel doUpdate(@RequestBody UserInfo dto, HttpServletRequest request) {
        final String action = "doUpdate";
        StopWatch sw = new StopWatch();
        log.info(START_LOG, action);
        try {
            UserInfo entity = service.findByUsername(dto.getUsername().trim());

            entity.setUsername(dto.getUsername().trim());
            entity.setFullName(dto.getFullName().trim());
            entity.setDob(dto.getDob());
            entity.setGender(dto.getGender());
            entity.setRoles(dto.getRoles());
            entity.setEmail(dto.getEmail().trim());
            entity.setPhone(dto.getPhone().trim());
            entity.setAddress(dto.getAddress().trim());
            entity.setIdDoctor(dto.getIdDoctor());
            service.update(entity, entity.getId());

            ResponseModel responseModel = new ResponseModel();
            responseModel.setStatusCode(HttpStatus.SC_OK);
            responseModel.setCode(ResponseFontendDefine.CODE_SUCCESS);
            return responseModel;
        } catch (Exception e) {
            throw handleException(e);
        } finally {
            log.info(END_LOG, action, sw.getTotalTimeSeconds());
        }
    }

//done
    @DeleteMapping("delete/{id}")
    public ResponseModel doDeleteLogic(@PathVariable Integer id, HttpServletRequest request)
            throws Exception{
        final String action = "doDelete";
        StopWatch sw = new StopWatch();
        log.info(START_LOG, action);
        ResponseModel responseModel = new ResponseModel();

        try {
            UserInfo entity = service.retrieve(id);
            entity.setStatus(2);
            service.update(entity, id);
            responseModel.setStatusCode(HttpStatus.SC_OK);
            responseModel.setCode(ResponseFontendDefine.CODE_SUCCESS);
            return responseModel;
        } catch (Exception e) {
            throw e;
        } finally {
            log.info(END_LOG, action, sw.getTotalTimeSeconds());
        }
    }


    //done
    @GetMapping("FindById/{id}")
    public ResponseModel FindById(@PathVariable Integer id, HttpServletRequest request)
            throws Exception{
        final String action = "FindById";
        StopWatch sw = new StopWatch();
        log.info(START_LOG, action);
        ResponseModel responseModel = new ResponseModel();

        try {
            Optional<UserInfoDTO> entity = service.findByIdUser(id);
            responseModel.setContent(entity);
            responseModel.setStatusCode(HttpStatus.SC_OK);
            responseModel.setCode(ResponseFontendDefine.CODE_SUCCESS);
            return responseModel;
        } catch (Exception e) {
            throw e;
        } finally {
            log.info(END_LOG, action, sw.getTotalTimeSeconds());
        }
    }


    //done
    @GetMapping("FindIdDoctor")
    public ResponseModel FindIdDoctor( HttpServletRequest request)
            throws Exception{
        final String action = "FindIdDoctor";
        StopWatch sw = new StopWatch();
        log.info(START_LOG, action);
        ResponseModel responseModel = new ResponseModel();

        try {
            List<UserInfoDTO> entity = service.FindIdDoctor(2);
            responseModel.setContent(entity);
            responseModel.setStatusCode(HttpStatus.SC_OK);
            responseModel.setCode(ResponseFontendDefine.CODE_SUCCESS);
            return responseModel;
        } catch (Exception e) {
            throw e;
        } finally {
            log.info(END_LOG, action, sw.getTotalTimeSeconds());
        }
    }


    //done
    @PostMapping("/change-password")
    public ResponseModel changePassword(@RequestBody Integer id, String password,  HttpServletRequest request) {
        final String action = "changePassword";
        StopWatch sw = new StopWatch();
        log.info(START_LOG, action);
        try {

            String passwordNew = config.passwordEncoder().encode(password);
            UserInfo entity = service.retrieve(id);
            entity.setPassword(passwordNew);
            service.update(entity, id);

            ResponseModel responseModel = new ResponseModel();
            responseModel.setStatusCode(HttpStatus.SC_OK);
            responseModel.setCode(ResponseFontendDefine.CODE_SUCCESS);
            return responseModel;
        } catch (Exception e) {
            throw handleException(e);
        } finally {
            log.info(END_LOG, action, sw.getTotalTimeSeconds());
        }
    }



    //done
    @GetMapping("/change-Status-online")
    public ResponseModel changeStatusOnline( HttpServletRequest request) {
        final String action = "changeStatausOnline";
        StopWatch sw = new StopWatch();
        log.info(START_LOG, action);
        try {
            Integer id = (int) getUserFromToken(request);
            UserInfo entity = service.retrieve(id);
            entity.setStatusOnline(1);
            service.update(entity, id);

            messagingTemplate.convertAndSend("/topic/log-act/Online" , id.toString());
            ResponseModel responseModel = new ResponseModel();
            responseModel.setStatusCode(HttpStatus.SC_OK);
            responseModel.setCode(ResponseFontendDefine.CODE_SUCCESS);
            return responseModel;
        } catch (Exception e) {
            throw handleException(e);
        } finally {
            log.info(END_LOG, action, sw.getTotalTimeSeconds());
        }
    }

    //done
    @GetMapping("/change-Status-offline")
    public ResponseModel changeStatusOffline( HttpServletRequest request) {
        final String action = "changeStatusOffline";
        StopWatch sw = new StopWatch();
        log.info(START_LOG, action);
        try {
            Integer id = (int) getUserFromToken(request);
            UserInfo entity = service.retrieve(id);
            entity.setStatusOnline(2);
            entity.setIdPeerjs("");
            service.update(entity, id);

            messagingTemplate.convertAndSend("/topic/log-act/Offline" , id.toString());
            ResponseModel responseModel = new ResponseModel();
            responseModel.setStatusCode(HttpStatus.SC_OK);
            responseModel.setCode(ResponseFontendDefine.CODE_SUCCESS);
            return responseModel;
        } catch (Exception e) {
            throw handleException(e);
        } finally {
            log.info(END_LOG, action, sw.getTotalTimeSeconds());
        }
    }
    //done
    @PostMapping("UpdatePeerJs")
    public ResponseModel doUpdatePeerJs(@RequestBody  String idPeerjs, HttpServletRequest request) {
        final String action = "doUpdatePeerJs";
        StopWatch sw = new StopWatch();
        log.info(START_LOG, action);
        try {
            UserInfo entity = service.retrieve(getUserFromToken(request));

            entity.setIdPeerjs(idPeerjs);
            service.update(entity, entity.getId());
            ResponseModel responseModel = new ResponseModel();
            responseModel.setStatusCode(HttpStatus.SC_OK);
            responseModel.setCode(ResponseFontendDefine.CODE_SUCCESS);
            return responseModel;
        } catch (Exception e) {
            throw handleException(e);
        } finally {
            log.info(END_LOG, action, sw.getTotalTimeSeconds());
        }
    }
}
