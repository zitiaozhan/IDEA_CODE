/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: Converter
 * Author:   郭新晔
 * Date:     2018/5/16 0016 10:44
 * Description: 类型转化器
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package newFeature;

/**
 * 〈一句话功能简述〉<br> 
 * 〈类型转化器〉
 *
 * @author 郭新晔
 * @create 2018/5/16 0016
 * @since 1.0.0
 */
@FunctionalInterface       //将lambda表达式映射到一个单抽象方法的接口上
public interface Converter<F,T>{
    T convert(F src);
}