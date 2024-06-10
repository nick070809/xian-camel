package org.xian.camel.cases.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author kaixiang.sun
 * @Date 2024/6/7 15:41
 */
@Data
public class CamelUser implements Serializable {

    private static final long serialVersionUID = -1202905659287390090L;
    private Long id ;
    private Date gmtCreate ;
    private Date gmtModified ;
    private String name ;
    private String address ;
    private Integer age ;

}
