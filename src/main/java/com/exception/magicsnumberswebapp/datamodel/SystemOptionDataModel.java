package com.exception.magicsnumberswebapp.datamodel;  
  
import com.exception.magicsnumbersws.entities.SystemOption;
import com.exception.magicsnumbersws.entities.User;
import java.util.List;  
import javax.faces.model.ListDataModel;  
import org.primefaces.model.SelectableDataModel;  
  

      
    
  
public class SystemOptionDataModel extends ListDataModel<SystemOption> implements SelectableDataModel<SystemOption> {    
  
    private List<SystemOption> systemOptions;

    public List<SystemOption> getSystemOptions() {
        return systemOptions;
    }

    public void setSystemOptions(List<SystemOption> systemOptions) {
        this.systemOptions = systemOptions;
    }
   
    
    public SystemOptionDataModel() {  
    }  
  
    public SystemOptionDataModel(List<SystemOption> data) {  
        super(data);  
        this.systemOptions = data;
    }  
    

    @Override      
    public SystemOption getRowData(String rowKey) {
         //In a real app, a more efficient way like a query by rowKey should be implemented to deal with huge data  
          
        List<SystemOption> users = (List<SystemOption>) getWrappedData();  
          
        for(SystemOption sysOption : users) {  
            if(sysOption.getName().equals(rowKey))  
                return sysOption;  
        }  
          
        return null;
    }
    @Override  
    public Object getRowKey(SystemOption sysOpt) {  
        return sysOpt.getName();
    } 
    
}