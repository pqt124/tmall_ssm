package pqt.tmall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pqt.tmall.pojo.Product;
import pqt.tmall.pojo.ProductImage;
import pqt.tmall.service.CategoryService;
import pqt.tmall.service.ProductImageService;
import pqt.tmall.service.ProductService;
import pqt.tmall.util.ImageUtil;
import pqt.tmall.util.UploadedImageFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("")
public class ProductImageController {
    @Autowired
    ProductImageService productImageService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductService productService;
    @RequestMapping("admin_productImage_list")
    public String list(int pid,Model model) {
        Product p =productService.get(pid);
        List<ProductImage> pisSingle = productImageService.list(pid, ProductImageService.type_single);
        List<ProductImage> pisDetail = productImageService.list(pid, ProductImageService.type_detail);
        model.addAttribute("p", p);
        model.addAttribute("pisSingle", pisSingle);
        model.addAttribute("pisDetail", pisDetail);
        return "admin/listProductImage";
    }

    @RequestMapping("admin_productImage_delete")
    public String delete(int id,HttpSession session) {
        ProductImage pi = productImageService.get(id);
        productImageService.delete(id);
        File imageFolder=null;
        if(pi.getType()=="type_single") {
            imageFolder = new File(session.getServletContext().getRealPath("img/productSingle"));
        }
        if(pi.getType()=="type_detail") {
            imageFolder = new File(session.getServletContext().getRealPath("img/type_detail"));
        }
        File file = new File(imageFolder,pi.getId()+".jpg");
        file.delete();
        return "redirect:admin_productImage_list?pid="+pi.getPid();
    }

   

    @RequestMapping("admin_productImage_add")
    public String add(ProductImage pi, Model model, UploadedImageFile uploadedImageFile,HttpSession session) throws IOException {
        productImageService.add(pi);
        File imageFolder=null;
       if(pi.getType().equals("type_single")) {
            imageFolder = new File(session.getServletContext().getRealPath("img/productSingle"));
       }
       if(pi.getType().equals("type_detail")) {
            imageFolder = new File(session.getServletContext().getRealPath("img/productdetail"));
       }
        System.out.println("pi.getId(): "+pi.getId());
        File file = new File(imageFolder,pi.getId()+".jpg");
        if (!file.getParentFile().exists())
            file.getParentFile().mkdirs();
        uploadedImageFile.getImage().transferTo(file);
        BufferedImage img = ImageUtil.change2jpg(file);
        ImageIO.write(img, "jpg", file);
        return "redirect:/admin_productImage_list?pid="+pi.getPid();
    }
}
