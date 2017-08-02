package demo.octopus.cn.core.service;

import demo.octopus.cn.core.service.responses.BaseResponse;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by JieGuo on 2017/8/2.
 */

public interface UserApiService {

    /**
     * login
     * @param userName user name
     * @param pwd user password
     * @param encType enc type
     * @param verifyCode verify code
     * @return Observable
     */
    @GET("user/login")
    Observable<BaseResponse<String>> login(
            @Query("userName") String userName,
            @Query("pwd") String pwd,
            @Query("encType") String encType,
            @Query("verifyCode") String verifyCode
    );

    @POST("user/changepwd")
    Observable<BaseResponse<String>> changePwd(
            @Query("username") String username,
            @Query("oldPwd") String oldPwd,
            @Query("currentPwd") String currentPwd
    );
}
