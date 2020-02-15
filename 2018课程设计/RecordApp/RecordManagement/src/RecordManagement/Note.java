package RecordManagement;
import java.io.*;

public class Note implements Serializable{
	private File imagePic;
	private int year;
	private int month;
	private int day;
	private String thing;
	private String cost;
	private String comment;
	private double realCost;
	/**
	 * 构造方法，空构造方法和有参构造方法
	 */
	public Note() {
		this.imagePic=null;
	}
	public Note(int year, int month, int day, String thing, String cost, String comment) {
		this.year = year;
		this.month = month;
		this.day = day;
		this.thing = thing;
		this.cost = cost;
		this.realCost=Double.valueOf(this.cost);
		this.comment = comment;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((comment == null) ? 0 : comment.hashCode());
		result = prime * result + ((cost == null) ? 0 : cost.hashCode());
		result = prime * result + day;
		result = prime * result + month;
		result = prime * result + ((thing == null) ? 0 : thing.hashCode());
		result = prime * result + year;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Note other = (Note) obj;
		if (comment == null) {
			if (other.comment != null)
				return false;
		} else if (!comment.equals(other.comment))
			return false;
		if (cost == null) {
			if (other.cost != null)
				return false;
		} else if (!cost.equals(other.cost))
			return false;
		if (day != other.day)
			return false;
		if (month != other.month)
			return false;
		if (thing == null) {
			if (other.thing != null)
				return false;
		} else if (!thing.equals(other.thing))
			return false;
		if (year != other.year)
			return false;
		return true;
	}
	
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public String getThing() {
		return thing;
	}
	public void setThing(String thing) {
		this.thing = thing;
	}
	public String getCost() {
		return cost;
	}
	public void setCost(String cost) {
		this.cost = cost;
		this.realCost=Double.valueOf(this.cost);
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public double getRealCost() {
		return realCost;
	}
	public void setRealCost() {
		this.realCost =Double.valueOf(this.cost);
	}
	public File getImagePic() {
		return imagePic;
	}
	public void setImagePic(File imagePic) {
		this.imagePic = imagePic;
	}
	@Override
	public String toString() {
		return "Note [year=" + year + ", month=" + month + ", day=" + day + ", thing=" + thing + ", cost=" + cost
				+ ", comment=" + comment + ", imagePic=" + imagePic + "]";
	}
	/**
	 * 排序方法
	 */
/*	@Override
	public int compareTo(Note o1) {
		// TODO Auto-generated method stub
		Note o=(Note) o1;
		if(this.year<o.year){
			return -1;
		}else if(this.year==o.year){
			if(this.month<o.month){
				return -1;
			}else if(this.month==o.month){
				if(this.day<o.day){
					return -1;
				}else if(this.day==o.day){
					return 0;
				}else{
					return 1;
				}
			}else{
				return 1;
			}
		}else{
			return 1;
		}
	}	*/
	
}
