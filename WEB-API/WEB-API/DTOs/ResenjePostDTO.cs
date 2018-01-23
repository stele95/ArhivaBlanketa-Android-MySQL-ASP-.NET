using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace WEB_API.DTOs
{
    public class ResenjePostDTO
    {
        public virtual int IdResenje { get; set; }
        public virtual int Blanket { get; set; }
        public virtual int Dodao { get; set; }
        public virtual byte[] ThumbnailInBytes { get; set; }
        public virtual byte[] ImageInBytes { get; set; }
    }
}