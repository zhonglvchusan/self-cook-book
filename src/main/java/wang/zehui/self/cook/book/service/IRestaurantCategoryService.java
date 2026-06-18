package wang.zehui.self.cook.book.service;

import com.baomidou.mybatisplus.extension.service.IService;
import wang.zehui.self.cook.book.common.domain.PageResult;
import wang.zehui.self.cook.book.domain.entity.RestaurantCategory;
import wang.zehui.self.cook.book.domain.request.RestaurantCategoryRequest;
import wang.zehui.self.cook.book.domain.response.RestaurantCategoryListResponse;
import wang.zehui.self.cook.book.domain.response.RestaurantCategorySearchRequest;

/**
 * 餐厅分类表(RestaurantCategory)表服务接口
 *
 * @author wangzehui
 * @since 2026-06-15 14:15:01
 */
public interface IRestaurantCategoryService extends IService<RestaurantCategory> {

    /**
     * @Description: 新增/修改分类
     * @param request 分类信息
     * @Return: java.lang.Boolean
     * @Author: wangzehui
     * @Date: 2026/6/15 14:46
     */
    Boolean addOrUpdateCategory(RestaurantCategoryRequest request);

    /**
     * @Description: 查询分类列表
     * @param request 查询参数
     * @Return: wang.zehui.self.cook.book.common.domain.PageResult<wang.zehui.self.cook.book.domain.response.RestaurantCategoryListResponse>
     * @Author: wangzehui
     * @Date: 2026/6/15 14:53
     */
    PageResult<RestaurantCategoryListResponse> getCategoryPageList(RestaurantCategorySearchRequest request);

    /**
     * @Description: 删除分类
     * @param id 分类id
     * @Return: java.lang.Boolean
     * @Author: wangzehui
     * @Date: 2026/6/15 14:54
     */
    Boolean deleteCategory(String id);
}

