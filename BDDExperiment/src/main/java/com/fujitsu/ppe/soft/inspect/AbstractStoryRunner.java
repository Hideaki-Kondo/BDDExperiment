package com.fujitsu.ppe.soft.inspect;

import java.text.SimpleDateFormat;
import java.util.List;

import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.io.CodeLocations;
import org.jbehave.core.io.StoryFinder;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.parsers.RegexPrefixCapturingPatternParser;
import org.jbehave.core.reporters.CrossReference;
import org.jbehave.core.reporters.FilePrintStreamFactory.ResolveToPackagedName;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.jbehave.core.steps.ParameterControls;
import org.jbehave.core.steps.ParameterConverters;
import org.jbehave.core.steps.ParameterConverters.DateConverter;
import org.jbehave.core.steps.SilentStepMonitor;
import org.junit.runner.RunWith;

import de.codecentric.jbehave.junit.monitoring.JUnitReportingRunner;

@RunWith(JUnitReportingRunner.class)
public abstract class AbstractStoryRunner extends JUnitStories {

  public AbstractStoryRunner() {
    configuredEmbedder().embedderControls()
      .doGenerateViewAfterStories(true)
      .doIgnoreFailureInStories(false)
      .doIgnoreFailureInView(true)
      .doVerboseFailures(true)
      .doFailOnStoryTimeout(true)
      .useThreads(1)
      .useStoryTimeouts("300");
  }

  @Override
  public Configuration configuration() {
    return new MostUsefulConfiguration()
      .useStoryReporterBuilder(
        new StoryReporterBuilder()
          .withDefaultFormats()
          .withPathResolver(new ResolveToPackagedName())
          .withFormats(
            Format.HTML,
            Format.XML,
            Format.TXT,
            Format.CONSOLE,
            Format.IDE_CONSOLE,
            Format.STATS)
          .withCrossReference(new CrossReference())
          .withFailureTrace(true)
          .withFailureTraceCompression(true))
       .useParameterControls(
         new ParameterControls()
           .useNameDelimiterLeft("<")
           .useNameDelimiterRight(">")
           .useDelimiterNamedParameters(true))
        .useParameterConverters(
          new ParameterConverters()
           .addConverters(new DateConverter(new SimpleDateFormat("yyyy-mm-dd"))))
        .useStepPatternParser(new RegexPrefixCapturingPatternParser("$"))
        .useStepMonitor(new SilentStepMonitor());
  }

  @Override
  public InjectableStepsFactory stepsFactory() {
    return new InstanceStepsFactory(
        configuration(), getStepsInstances());
  }

  @Override
  public List<String> storyPaths() {
    String codeLocation = CodeLocations.codeLocationFromClass(
                               this.getClass()).getFile();
    return new StoryFinder().findPaths(codeLocation,
                               getIncludeStoryPattern(),
                               getExcludeStoryPattern());
  }

  /**
   * ステップを実装したインスタンスのリストを返却する。
   * 
   * @return ステップインスタンスのリスト
   */
  protected abstract List<?> getStepsInstances();

  /**
   * 取り込むストーリーファイルのパスのパターンを返却する。
   * 
   * @return パスのリスト
   */
  protected abstract List<String> getIncludeStoryPattern();

  /**
   * 除外するストーリーファイルのパスのパターンを返却する。
   * 
   * @return パスのリスト
   */
  protected abstract List<String> getExcludeStoryPattern();

}
