package org.empre.bean;

import java.util.List;

public class FetchFilterCriteria {
    private String bpname;
    private String lineitem;
    private String lineitem_fields;
    private FilterCriteria filter_criteria;
    private String record_fields;

    public FetchFilterCriteria(String bpname, String lineitem, String lineitem_fields, FetchFilterCriteria.FilterCriteria filter_criteria, String record_fields )
    {
       this.bpname = bpname;
       this.lineitem = lineitem;
       this.lineitem_fields = lineitem_fields;
       this.filter_criteria = filter_criteria;
       this.record_fields = record_fields;
       }


    public static class FilterCriteria {
        private String join;
        private List<Filter> filter;


        public FilterCriteria(String join, List<Filter> filter){
            this.join = join;
            this.filter = filter;
            
            }

    }



    public static class Filter {
        private String field;
        private String value;
        private String condition_type;

        public Filter(String field, String value, String condition_type){
            this.field = field;
            this.value = value;
            this.condition_type = condition_type;
        
         }
        
        
    }

    }
