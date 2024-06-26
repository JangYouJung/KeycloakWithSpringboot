package woongjin.bi.backend.api.dashboard.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import woongjin.bi.backend.api.auth.AuthService;
import woongjin.bi.backend.api.dashboard.dto.DashboardRequestDto;
import woongjin.bi.backend.api.dashboard.service.DashboardService;
import woongjin.bi.backend.domain.entity.User;
import woongjin.bi.backend.exception.ForbiddenException;
import woongjin.bi.backend.util.CurrentUser;
import woongjin.bi.backend.util.ResponseCode;
import woongjin.bi.backend.util.ResponseData;
import woongjin.bi.backend.util.SliceResponse;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;
    private final AuthService authService;

    @GetMapping()
    @Operation(summary = "대시보드 조회", description = "우리 회사 대시보드를 조회합니다. \n  sort 값은 꼭 'createdAt'으로 지정해주세요")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "OK", description = "대시보드 조회 성공"),
            @ApiResponse(responseCode = "NOT_FOUND", description = "대시보드 조회 실패")
    })
    public ResponseEntity<ResponseData<SliceResponse>> getDashboard(
            @CurrentUser User user,
            Pageable pageable
    ){
        try{
            return ResponseData.toResponseEntity( ResponseCode.SEARCH_DASHBOARD_SUCCESS, dashboardService.readDashboard( user, pageable ));

        }catch( NoSuchElementException e ){
            return ResponseData.toResponseEntity(ResponseCode.DASHBOARD_SEARCH_FAIL, null);
        }
    }


    @PostMapping()
    //@RolesAllowed({ "ADMIN" })
    @Operation(summary = "대시보드 생성", description = "대시보드를 추가합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "OK", description = "대시보드 생성 성공"),
            @ApiResponse(responseCode = "BAD_REQUEST", description = "대시보드 생성 실패")
    })
    public ResponseEntity<ResponseData> createDashboard(
            @CurrentUser User user,
            @RequestBody @Valid DashboardRequestDto dashboardRequestDto
    ){
        try{
            return ResponseData.toResponseEntity(dashboardService.createDashboard(user, dashboardRequestDto));
        }
        catch ( NoSuchElementException e ){
            return ResponseData.toResponseEntity(ResponseCode.CREATE_DASHBOARD_FAIL);
        }

    }

    @PutMapping("/{dashboardId}")
    //@RolesAllowed({ "ADMIN" })
    @Operation(summary = "대시보드 수정", description = "대시보드를 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "OK", description = "대시보드 수정 성공"),
            @ApiResponse(responseCode = "BAD_REQUEST", description = "대시보드 수정 실패"),
            @ApiResponse(responseCode = "FORBIDDEN", description = "대시보드 수정 권한 없음")
    })
    public ResponseEntity<ResponseData> modifyDashboard(
            @CurrentUser User user,
            @RequestBody @Valid DashboardRequestDto dashboardRequestDto,
            @PathVariable("dashboardId") Long dashboardId
    ){
        try{
            // TODO user Role에 따라 수정 권한이 있는지 확인하라
            return ResponseData.toResponseEntity(dashboardService.modifyDashboard(user, dashboardRequestDto, dashboardId ));
        }
        catch ( NoSuchElementException e ){
            return ResponseData.toResponseEntity(ResponseCode.MODIFY_DASHBOARD_FAIL);
        }
        catch ( ForbiddenException e ){
            return ResponseData.toResponseEntity(ResponseCode.DASHBOARD_MODIFY_FORBIDDEN_NOT_OWNER);
        }
    }

    @DeleteMapping("/{dashboardId}")
    //@RolesAllowed({ "ADMIN" })
    @Operation(summary = "대시보드 삭제", description = "대시보드를 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "OK", description = "대시보드 삭제 성공"),
            @ApiResponse(responseCode = "BAD_REQUEST", description = "대시보드 삭제 실패"),
            @ApiResponse(responseCode = "FORBIDDEN", description = "대시보드 삭제 권한 없음")
    })
    public ResponseEntity<ResponseData> deleteDashboard(
            @CurrentUser User user,
            @PathVariable("dashboardId") Long dashboardId
    ){
        try{
            // TODO user Role에 따라 삭제 권한이 있는지 확인하라
            return ResponseData.toResponseEntity(dashboardService.deleteDashboard(dashboardId, user));
        }
        catch ( NoSuchElementException e ){
            return ResponseData.toResponseEntity(ResponseCode.DELETE_DASHBOARD_FAIL);
        }
        catch ( ForbiddenException e ){
            return ResponseData.toResponseEntity(ResponseCode.DASHBOARD_DELETE_FORBIDDEN_NOT_OWNER);
        }

    }


}
