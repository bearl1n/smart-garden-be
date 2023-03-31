package com.gmv.smartgardenbe.device.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class RelayDto{
        Long id;
        String name;
        boolean status;
}
