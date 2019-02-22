package com.tensquare.friend.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @Description TODO
 * @Author shawn
 * @create 2019/2/22 0022
 */
@Entity
@Table(name="tb_nofriend")
@IdClass(NoFriend.class)
@Data
@AllArgsConstructor
public class NoFriend implements Serializable {
    @Id
    private String userId;
    @Id
    private String friendId;

    public NoFriend() {
    }
}
