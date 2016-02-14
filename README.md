# HierarchyViewerSample
ViewServer and ViewServer-sample are taken from https://github.com/romainguy/ViewServer. 
It is a workaround module for the devices which are nor development devices.

row_beatles_right.xml and row_beatles_wrong.xml show the right and wrong ways of implementing layouts.

Please change the below part in your MainActivity according to which row layout file you used.

 public ViewHolder(LinearLayout v) {
    super(v);
    ...
  }
                
  LinearLayout v = (LinearLayout)LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_beatles_wrong, parent, false);              
