package de.cas_ual_ty.guncus.item.attachments;

import de.cas_ual_ty.guncus.item.ItemAttachment;

public class Accessory extends ItemAttachment
{
    public static final Accessory DEFAULT = new Accessory();
    
    protected Laser laser;
    
    public Accessory(Properties properties)
    {
        super(properties);
        
        this.laser = null;
    }
    
    protected Accessory()
    {
        this(new Properties());
    }
    
    @Override
    public EnumAttachmentType getType()
    {
        return EnumAttachmentType.ACCESSORY;
    }
    
    public Laser getLaser()
    {
        return this.laser;
    }
    
    public Accessory setLaser(Laser laser)
    {
        this.laser = laser;
        return this;
    }
    
    public static class Laser
    {
        protected float r;
        protected float g;
        protected float b;
        
        protected double maxRange;
        
        protected boolean isBeam;
        protected boolean isPoint;
        protected boolean isRangeFinder;
        
        public Laser(float r, float g, float b, double maxRange, boolean isBeam, boolean isPoint, boolean isRangeFinder)
        {
            this.r = r;
            this.g = g;
            this.b = b;
            this.maxRange = maxRange;
            this.isBeam = isBeam;
            this.isPoint = isPoint;
            this.isRangeFinder = isRangeFinder;
        }
        
        public float getR()
        {
            return this.r;
        }
        
        public float getG()
        {
            return this.g;
        }
        
        public float getB()
        {
            return this.b;
        }
        
        public double getMaxRange()
        {
            return this.maxRange;
        }
        
        public boolean isBeam()
        {
            return this.isBeam;
        }
        
        public boolean isPoint()
        {
            return this.isPoint;
        }
        
        public boolean isRangeFinder()
        {
            return this.isRangeFinder;
        }
    }
}
