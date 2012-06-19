/*
 * Copyright (C) 2009-2012 SonarSource SA
 * All rights reserved
 * mailto:contact AT sonarsource DOT com
 */
package com.sonar.csharp.squid.parser.rules.expressions;

import static com.sonar.sslr.test.parser.ParserMatchers.*;
import static org.junit.Assert.*;

import java.nio.charset.Charset;

import org.junit.Before;
import org.junit.Test;

import com.sonar.csharp.squid.CSharpConfiguration;
import com.sonar.csharp.squid.api.CSharpGrammar;
import com.sonar.csharp.squid.parser.CSharpParser;
import com.sonar.sslr.impl.Parser;

public class MemberAccessTest {

  private final Parser<CSharpGrammar> p = CSharpParser.create(new CSharpConfiguration(Charset.forName("UTF-8")));
  private final CSharpGrammar g = p.getGrammar();

  @Before
  public void init() {
    p.setRootRule(g.memberAccess);
  }

  @Test
  public void testOk() {
    g.typeArgumentList.mock();
    g.predefinedType.mock();
    g.qualifiedAliasMember.mock();
    assertThat(p, parse("predefinedType.id"));
    assertThat(p, parse("predefinedType.id typeArgumentList"));
    assertThat(p, parse("qualifiedAliasMember.id"));
  }

  @Test
  public void testKo() {
    g.qualifiedAliasMember.mock();
    g.typeArgumentList.mock();
    assertThat(p, notParse(""));
    assertThat(p, notParse("qualifiedAliasMember.id typeArgumentList"));
  }

  @Test
  public void testRealLife() throws Exception {
    assertThat(p, parse("int.MaxValue"));
  }

}
