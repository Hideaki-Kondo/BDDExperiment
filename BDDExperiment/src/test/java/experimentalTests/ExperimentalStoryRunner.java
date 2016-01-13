package experimentalTests;

import java.util.Arrays;
import java.util.List;

import com.fujitsu.ppe.soft.inspect.AbstractStoryRunner;

import experimentalTests.steps.ActionSteps;

public class ExperimentalStoryRunner extends AbstractStoryRunner {
 
	/**
	 * ステップを実装したインスタンスのリストを返却する。
	 * @return ステップインスタンスのリスト
	 */
	protected List<?> getStepsInstances() {
		// ステップ実装クラスのインスタンスをリストで返却する。リスト内には複数のステップ実装クラスを記載して良い。
		return Arrays.asList(new ActionSteps());
	}

	/**
	 * 取り込むストーリーファイルのパスのパターンを返却する。
	 * @return　パスのリスト 
	 */
	protected List<String> getIncludeStoryPattern() {
		// テスト実施対象とするストーリーファイルのパスを指定する。複数のパスを正規表現で指定可能。
		return Arrays.asList("Sample.story");
	}

	/**
	 * 除外するストーリーファイルのパスのパターンを返却する。
	 * @return パスのリスト
	 */
	protected List<String> getExcludeStoryPattern() {
		// テスト除外対象とするストーリーファイルのパスを指定する。複数のパスを正規表現で指定可能。
		return Arrays.asList("*@*");
	}
}
