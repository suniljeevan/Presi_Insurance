package com.insurance.ClaimsManagement.ResponseTemplateVo;

import com.insurance.ClaimsManagement.entity.Claims;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseTemplate
{
    private Policy policy;
    private Claims claims;
}
