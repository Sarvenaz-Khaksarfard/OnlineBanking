import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Transactions {

	@Id @GeneratedValue (strategy = GenerationType.AUTO)
	private int id;
	private String type;
	private BigDecimal amount;
	private BigDecimal current_balance;

	public BigDecimal getCurrent_balance() {
		return current_balance;
	}

	public void setCurrent_balance(BigDecimal current_balance) {
		this.current_balance = current_balance;
	}

	@Temporal (TemporalType.DATE)
	private Date date;
	
	@ManyToOne
	@JoinColumn (name = "accountNumber")
	private Customer customer;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}


}
