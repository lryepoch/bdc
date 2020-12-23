package com.lryepoch.dao.jpa;

import com.lryepoch.entity.product.ProductProfessionalParameters;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author lryepoch
 * @date 2020/10/13 10:37
 * @description TODO
 */
public interface ProfessionalParamMapper extends JpaRepository<ProductProfessionalParameters, Integer> {
    /**
    * @description 根据机型找到对应的专业参数
    * @author lryepoch
    * @date 2020/11/12 8:50
    *
    */
    ProductProfessionalParameters findByModel(String model);

    /**
    * @description 根据机型列表找到对应的专业参数列表
    * @author lryepoch
    * @date 2020/11/12 8:52
    *
    */
    @Query(value = "select * from product_professional_parameters pro where pro.model in (:model) and deleted=1", nativeQuery = true)
    List<ProductProfessionalParameters> findByModel(@Param("model") List<String> model);
}
