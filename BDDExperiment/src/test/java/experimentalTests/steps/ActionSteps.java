package experimentalTests.steps;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;

import org.jbehave.core.annotations.BeforeScenario;
import org.jbehave.core.annotations.Composite;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

public class ActionSteps {

  // 各ステップで共有するオブジェクトの定義。
  //   注: 直前のシナリオの実行結果を引き継いでしまう、シナリオ実行前に初期化(@BeforeScenario)することを推奨。
	BigDecimal savingBalance = BigDecimal.ZERO;
	BigDecimal cashingBalance = BigDecimal.ZERO;
	boolean hasTransactionError = false;
	
	@BeforeScenario
	public void reset() {
	  savingBalance = BigDecimal.ZERO;
	  cashingBalance = BigDecimal.ZERO;
	  hasTransactionError = false;
	}
	
	@Given("普通預金口座に $savingAmount 円が預金されている。")
	public void setSavingBalance (long amount) {
		savingBalance = BigDecimal.valueOf(amount);
	}
	
	@Given("当座預金口座に $cashingAmount 円が預金されている。")
	public void setCashingBalance (long amount) {
		cashingBalance = BigDecimal.valueOf(amount);
	}
	
	@When("普通預金口座から当座預金口座に $transferAmount 円を振り替えたならば")
	public void transferSavingToCaching(long amount) {
		BigDecimal transferAmount = BigDecimal.valueOf(amount);
		if ( savingBalance.compareTo(transferAmount) >= 0) {
			savingBalance = savingBalance.subtract(transferAmount);
			cashingBalance = cashingBalance.add(transferAmount);
		} else {
			hasTransactionError = true;
		}
	}
	
	@Then("普通預金口座の残高は $savingBalance 円となる。")
	public void assertSavingBalance(long balance) {
		assertThat(savingBalance, is(equalTo(BigDecimal.valueOf(balance))));
	}
	
	@Then("当座預金口座の残高は $cashingBalance 円となる。")
	public void assertCashingBalance(long balance) {
		assertThat(cashingBalance, is(equalTo(BigDecimal.valueOf(balance))));
	}
	
	@Then(value="普通預金口座の残高は $savingBalance 円, 当座預金口座の残高は $cashingBalance 円となる。", priority=1)
	public void assertBothBalance(long sBalance, long cBalance) {
	  assertSavingBalance(sBalance);
	  assertCashingBalance(cBalance);
	}

	
	@Then("振替処理はエラーとなる。")
	public void hasTransactionError() {
		assertThat(hasTransactionError, is(true));
	}
}
