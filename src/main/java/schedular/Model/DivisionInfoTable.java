package schedular.Model;
/**
 * @author Syed Khurshid
 */
/**
 * This is to update the table under the reports section
 */
public class DivisionInfoTable {
    private String division;
    private Integer customerTotal;
    /**
     * This is the default Constructor constructor
     * @param division string of the division name
     * @param customerTotal This is the NUmber of customers under each division
     */
    public DivisionInfoTable(String division, Integer customerTotal) {
        this.division = division;
        this.customerTotal = customerTotal;
    }
    /**
     * This is to get the Division Name
     * @return String division name
     */
    public String getDivision() {
        return division;
    }
    /**
     * This is to set the Division 
     * @param division is the Division String
     */
    public void setDivision(String division) {
        this.division = division;
    }
    /**
     * This is to Get the Total Customers we have
     * @return Integer of the total customers under each Division
     */
    public Integer getCustomerTotal() {
        return customerTotal;
    }
    
    /**
     * This is to set the Customer Totals under each division
     */
    public void setCustomerTotal(Integer customerTotal) {
        this.customerTotal = customerTotal;
    }
    
}
