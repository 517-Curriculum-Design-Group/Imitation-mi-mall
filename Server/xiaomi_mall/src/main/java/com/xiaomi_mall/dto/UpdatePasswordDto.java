package com.xiaomi_mall.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePasswordDto {
    String rawPassword;
    String newPassword;
    String checkNewPassword;
}
