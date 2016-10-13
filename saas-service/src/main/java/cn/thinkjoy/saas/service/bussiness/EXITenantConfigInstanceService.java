package cn.thinkjoy.saas.service.bussiness;

import cn.thinkjoy.common.dao.IBaseDAO;
import cn.thinkjoy.common.domain.BaseDomain;
import cn.thinkjoy.common.service.IBaseService;
import cn.thinkjoy.common.service.IPageService;

/**
 * Created by douzy on 16/10/13.
 */
public interface EXITenantConfigInstanceService<D extends IBaseDAO<T>, T extends BaseDomain> extends IBaseService<D, T>,IPageService<D, T> {

    /**
     * 生成租户自选表头
     * @param type class:班级  teacher:教师
     * @param ids  表头id集
     * @param tnId 租户
     *
     * @return
     *
     * 0:成功
     * -1:失败,系统错误
     * 1:参数错误
     * 2:该租户不存在
     * 3:选项集校验失败
     */
    public String importConfig(String type,String ids,Integer tnId);

    /**
     * 逐条删除租户表头  by
     * @param id  表头ID
     * @return
     */
    public boolean removeConfigDataById(Integer id);

    /**
     * 批量删除租户表头
     *
     * @param ids
     */
    boolean removeTeantConfigs(String ids);

    /**
     * 租户表头排序
     * @param type class:班级 teacher:教师
     * @param ids  需要调换的两个表头ID
     * @return
     */
    boolean sortTeantConfig(String type,String ids);
}
