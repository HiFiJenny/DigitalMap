package com.comac.enterprise.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.comac.common.log.annotation.Log;
import com.comac.common.log.enums.BusinessType;
import com.comac.common.security.annotation.RequiresPermissions;
import com.comac.enterprise.domain.SempEnterpriseInfo;
import com.comac.enterprise.service.ISempEnterpriseInfoService;
import com.comac.common.core.web.controller.BaseController;
import com.comac.common.core.web.domain.AjaxResult;
import com.comac.common.core.utils.poi.ExcelUtil;
import com.comac.common.core.web.page.TableDataInfo;

/**
 * 企业信息Controller
 * 
 * @author comac
 * @date 2023-03-29
 */
@RestController
@RequestMapping("/enterprise/info")
public class SempEnterpriseInfoController extends BaseController
{
    @Autowired
    private ISempEnterpriseInfoService sempEnterpriseInfoService;

    /**
     * 查询企业信息列表
     */
    @RequiresPermissions("enterprise:info:list")
    @GetMapping("/list")
    public TableDataInfo list(SempEnterpriseInfo sempEnterpriseInfo)
    {
        startPage();
        List<SempEnterpriseInfo> list = sempEnterpriseInfoService.selectSempEnterpriseInfoList(sempEnterpriseInfo);
        return getDataTable(list);
    }

    /**
     * 导出企业信息列表
     */
    @RequiresPermissions("enterprise:info:export")
    @Log(title = "企业信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SempEnterpriseInfo sempEnterpriseInfo)
    {
        List<SempEnterpriseInfo> list = sempEnterpriseInfoService.selectSempEnterpriseInfoList(sempEnterpriseInfo);
        ExcelUtil<SempEnterpriseInfo> util = new ExcelUtil<SempEnterpriseInfo>(SempEnterpriseInfo.class);
        util.exportExcel(response, list, "企业信息数据");
    }

    /**
     * 获取企业信息详细信息
     */
    @RequiresPermissions("enterprise:info:query")
    @GetMapping(value = "/{enterpriseId}")
    public AjaxResult getInfo(@PathVariable("enterpriseId") Long enterpriseId)
    {
        return success(sempEnterpriseInfoService.selectSempEnterpriseInfoByEnterpriseId(enterpriseId));
    }

    /**
     * 新增企业信息
     */
    @RequiresPermissions("enterprise:info:add")
    @Log(title = "企业信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SempEnterpriseInfo sempEnterpriseInfo)
    {
        return toAjax(sempEnterpriseInfoService.insertSempEnterpriseInfo(sempEnterpriseInfo));
    }

    /**
     * 修改企业信息
     */
    @RequiresPermissions("enterprise:info:edit")
    @Log(title = "企业信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SempEnterpriseInfo sempEnterpriseInfo)
    {
        return toAjax(sempEnterpriseInfoService.updateSempEnterpriseInfo(sempEnterpriseInfo));
    }

    /**
     * 删除企业信息
     */
    @RequiresPermissions("enterprise:info:remove")
    @Log(title = "企业信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{enterpriseIds}")
    public AjaxResult remove(@PathVariable Long[] enterpriseIds)
    {
        return toAjax(sempEnterpriseInfoService.deleteSempEnterpriseInfoByEnterpriseIds(enterpriseIds));
    }
}
