package com.codevui.realworldapp.entity;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_tbl")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class User implements UserDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true)
    private String email;
    private String password;
    @Column(unique = true)
    private String username;
    private String bio;
    private String image;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_follow", joinColumns = @JoinColumn(name = "follower_id"), inverseJoinColumns = @JoinColumn(name = "user_id")) // user
    private Set<User> followers; // nhung ai dang follow toi                                                                              // dang
                                                                                                                                          // follow
                                                                                                                                          // ai
    

    @ManyToMany(mappedBy = "followers")
    private Set<User> followings; // dang follow ai

    @OneToMany(mappedBy = "author")
    private List<Article> articles;

    @ManyToMany
    @JoinTable(name = "user_favorite_article", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "article_id"))
    private Set<Article> articleFavorited; // nhung bai article ma user da like

    @OneToMany(mappedBy = "author")
    private List<Comment> comments;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "users_roles", 
                joinColumns = @JoinColumn(name = "user_id"), 
                inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public Set<Role> getRoles() {
        Set<Role> set = new HashSet<>();
        if (this.roles == null || this.roles.isEmpty()) this.roles = set;
        return this.roles;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();    
        for (Role role : roles) {
        authorities.add(new SimpleGrantedAuthority(role.getName()));
        }      
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return true;
    }

}
