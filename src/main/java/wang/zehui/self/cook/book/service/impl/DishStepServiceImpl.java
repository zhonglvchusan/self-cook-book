package wang.zehui.self.cook.book.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.annotation.Transactional;
import wang.zehui.self.cook.book.common.domain.BusinessException;
import wang.zehui.self.cook.book.common.enums.ErrorCodeEnum;
import wang.zehui.self.cook.book.dao.DishStepDao;
import wang.zehui.self.cook.book.domain.entity.DishStep;
import wang.zehui.self.cook.book.domain.request.DishStepRequest;
import wang.zehui.self.cook.book.domain.response.DishStepListResponse;
import wang.zehui.self.cook.book.service.IDishStepService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜品步骤表(DishStep)表服务实现类
 *
 * @author wangzehui
 * @since 2026-06-23 14:18:10
 */
@Service
public class DishStepServiceImpl extends ServiceImpl<DishStepDao, DishStep> implements IDishStepService {

    @Override
    @Transactional(rollbackFor = {Exception.class, Error.class, BusinessException.class})
    public Boolean addOrUpdateDishStep(List<DishStepRequest> dishStepRequests) {
        // 菜品步骤为空，直接返回
        if (CollectionUtils.isEmpty(dishStepRequests)) {
            return true;
        }

        // 先删除之前的步骤
        this.removeByDishId(dishStepRequests.get(0).getDishId());

        List<DishStep> steps = dishStepRequests.stream().map(dishStepRequest -> {
            DishStep dishStep = new DishStep();
            BeanUtils.copyProperties(dishStepRequest, dishStep);
            return dishStep;
        }).collect(Collectors.toList());

        // 保存新步骤
        try {
            this.saveOrUpdateBatch(steps);
        } catch (DuplicateKeyException e) {
            throw new BusinessException(ErrorCodeEnum.STEP_REPEAT);
        }
        return true;
    }

    @Override
    public List<DishStepListResponse> getDishSteps(String dishId) {
        List<DishStep> dishSteps = this.list(Wrappers.<DishStep>lambdaQuery()
                .eq(DishStep::getDishId, dishId));

        return dishSteps.stream()
                // 根据步骤排序
                .sorted(Comparator.comparingInt(DishStep::getStepNumber))
                .map(dishStep -> {
                    DishStepListResponse response = new DishStepListResponse();
                    BeanUtils.copyProperties(dishStep, response);
                    return response;
                }).collect(Collectors.toList());
    }

    /**
     * @Description: 根据菜品id删除菜品步骤
     * @param dishId 菜品id
     * @Return: void
     * @Author: wangzehui
     * @Date: 2026/6/23 14:35
     */
    private void removeByDishId(String dishId) {
        this.remove(Wrappers.<DishStep>lambdaQuery()
                .eq(DishStep::getDishId, dishId));
    }
}

