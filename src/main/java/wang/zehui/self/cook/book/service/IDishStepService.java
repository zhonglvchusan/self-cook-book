package wang.zehui.self.cook.book.service;

import com.baomidou.mybatisplus.extension.service.IService;
import wang.zehui.self.cook.book.domain.entity.DishStep;
import wang.zehui.self.cook.book.domain.request.DishStepRequest;
import wang.zehui.self.cook.book.domain.response.DishStepListResponse;

import java.util.List;

/**
 * 菜品步骤表(DishStep)表服务接口
 *
 * @author wangzehui
 * @since 2026-06-23 14:18:09
 */
public interface IDishStepService extends IService<DishStep> {

    /**
     * @Description: 新增/修改菜品步骤
     * @param dishStepRequests 菜品步骤信息
     * @Return: java.lang.Boolean
     * @Author: wangzehui
     * @Date: 2026/6/23 14:25
     */
    Boolean addOrUpdateDishStep(List<DishStepRequest> dishStepRequests);

    /**
     * @Description: 获取菜品步骤
     * @param dishId 菜品id
     * @Return: java.util.List<wang.zehui.self.cook.book.domain.response.DishStepListResponse>
     * @Author: wangzehui
     * @Date: 2026/6/23 14:28
     */
    List<DishStepListResponse> getDishSteps(String dishId);

}

