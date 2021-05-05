package com.example.snowball.web;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.mock.env.MockEnvironment;

public class ProfileControllerUnitTest {

  @Test
  public void real_profile이_조회된다() {
    //given
    String expectedProfile = "real";
    MockEnvironment env = new MockEnvironment();
    env.addActiveProfile(expectedProfile);
    env.addActiveProfile("oauth");
    env.addActiveProfile("real-db");

    ProfileController controller = new ProfileController(env);

    //when
    String profile = controller.profile();

    //then
    Assertions.assertEquals(profile, expectedProfile);
  }

  @Test
  public void real_profile이_없으면_첫번쨰가_조회된다() {
    //given
    String expectedProfile = "oauth";
    MockEnvironment env = new MockEnvironment();

    env.addActiveProfile(expectedProfile);
    env.addActiveProfile("real-db");

    ProfileController controller = new ProfileController(env);

    //when
    String profile = controller.profile();

    //then
    Assertions.assertEquals(profile, expectedProfile);
  }

  @Test
  public void active_profile이_없으면_default가_조회된다() throws Exception {
    //given
    String expectedProfile = "default";
    MockEnvironment env = new MockEnvironment();
    ProfileController controller = new ProfileController(env);
    //when
    String profile = controller.profile();
    //then
    Assertions.assertEquals(profile, expectedProfile);
  }

}
