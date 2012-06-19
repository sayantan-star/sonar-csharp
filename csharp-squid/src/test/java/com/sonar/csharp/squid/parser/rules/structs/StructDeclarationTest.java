/*
 * Copyright (C) 2009-2012 SonarSource SA
 * All rights reserved
 * mailto:contact AT sonarsource DOT com
 */
package com.sonar.csharp.squid.parser.rules.structs;

import static com.sonar.sslr.test.parser.ParserMatchers.*;
import static org.junit.Assert.*;

import java.nio.charset.Charset;

import org.junit.Before;
import org.junit.Test;

import com.sonar.csharp.squid.CSharpConfiguration;
import com.sonar.csharp.squid.api.CSharpGrammar;
import com.sonar.csharp.squid.parser.CSharpParser;
import com.sonar.sslr.impl.Parser;

public class StructDeclarationTest {

  private final Parser<CSharpGrammar> p = CSharpParser.create(new CSharpConfiguration(Charset.forName("UTF-8")));
  private final CSharpGrammar g = p.getGrammar();

  @Before
  public void init() {
    p.setRootRule(g.structDeclaration);
    g.attributes.mock();
    g.typeParameterList.mock();
    g.structInterfaces.mock();
    g.typeParameterConstraintsClauses.mock();
    g.structBody.mock();
  }

  @Test
  public void testOk() {
    assertThat(p, parse("struct id structBody"));
    assertThat(p, parse("attributes new partial struct id typeParameterList structInterfaces typeParameterConstraintsClauses structBody;"));
    assertThat(p, parse("public protected internal private struct id structBody"));
  }

}
