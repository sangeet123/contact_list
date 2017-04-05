package contactlist.exceptions;

/**
 * Created by sangeet on 4/4/2017.
 */
public class NotFoundException extends RuntimeException {
  public NotFoundException(final String mesg){
    super(mesg);
  }
  public NotFoundException(){
  }
}
