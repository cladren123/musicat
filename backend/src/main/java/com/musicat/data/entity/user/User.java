package com.musicat.data.entity.user;


import com.musicat.data.entity.user.Authority;
import com.sun.istack.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "user ")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_seq")
    private long userSeq;

    @NotNull
    @Column(name = "user_id")
    private String userId;

    @NotNull
    @Column(name = "user_nickname")
    private String userNickname;

    @NotNull
    @Column(name = "user_profile_image")
    private String userProfileImage;

    @NotNull
    @Column(name = "user_thumbnail_image")
    private String userThumbnailImage;

    @NotNull
    @Column(name = "user_email")
    private String userEmail;

    @NotNull
    @CreatedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "user_created_at")
    private LocalDateTime userCreatedAt;

    @NotNull
    @Column(name = "user_money")
    private long userMoney;

    // MoneyLog 와 1:N 연관관계 설정
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<MoneyLog> userMoneyLogList = new ArrayList<>();



    @ManyToMany
    @JoinTable(
            name = "user_authority",
            joinColumns = {@JoinColumn(name = "user_seq", referencedColumnName = "user_seq")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
    private Set<Authority> userAuthority = new HashSet<>();


    @NotNull
    @Column(name = "user_warn_count")
    private int userWarnCount;

    @NotNull
    @Column(name = "user_unread_message")
    private int userUnreadMessage;

    /*

    boolean type getter는 is~ 로 구현된다.
    isUserIsDarkmode

    */
    @NotNull
    @Column(name = "user_is_darkmode")
    private boolean userIsDarkmode;

    @NotNull
    @Column(name = "user_is_Chatting_ban")
    private boolean userIsChattingBan;

    @NotNull
    @Column(name = "user_is_ban")
    private boolean userIsBan;

    @NotNull
    @Column(name = "user_is_user")
    private boolean userIsUser;

    /*
    @PrePersist
    JPA 엔티티 라이프사이클 이벤트 중 하나
    엔티티가 저장되기 전에 호출되는 메소드에 적용하는 어노테이션
    @PrePersis가 적둉된 메소드는 엔티티가 저장되기 전에 자동으로 호출
    전 처리 작업을 수행
     */
    @PrePersist
    public void prePersist() {
        if(userEmail == null) {
            this.userEmail = "";
        }
        this.userCreatedAt = LocalDateTime.now();
        this.userMoney = 0;
        this.userWarnCount = 0;
        this.userUnreadMessage = 0;
        this.userIsDarkmode = false;
        this.userIsChattingBan = false;
        this.userIsBan = false;
        this.userIsUser = false;
    }



}
