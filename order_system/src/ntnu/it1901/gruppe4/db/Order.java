package ntnu.it1901.gruppe4.db;

import java.util.Date;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "order")
public class Order {
    @DatabaseField(canBeNull = false, generatedId = true)
	int idorder;

	@DatabaseField(useGetSet = true)
	Date ordertime;

	@DatabaseField(useGetSet = true)
	Date deliverytime;

    @DatabaseField(useGetSet = true)
	int state;

    @DatabaseField(useGetSet = true, foreign = true, canBeNull = false)
	Address idaddress;
	
    //	PS: We determine customer trough address
    //@DatabaseField(useGetSet = true, foreign = true, canBeNull = false)
	//int idcustomer;
    
    public Order()
    {
    }
    
	public Order(Address address)
    {
    	setIdaddress(address);
    	setDeliverytime(new Date());	//	Supposedly this is the current time
    	setState(0);
    }
	
    public Address getIdaddress() {
		return idaddress;
	}

	public void setIdaddress(Address idaddress) {
		this.idaddress = idaddress;
	}

    public int getIdorder() {
		return idorder;
	}

    public Date getOrdertime() {
		return ordertime;
	}

	public void setOrdertime(Date ordertime) {
		this.ordertime = ordertime;
	}

	public Date getDeliverytime() {
		return deliverytime;
	}

	public void setDeliverytime(Date deliverytime) {
		this.deliverytime = deliverytime;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}
}
