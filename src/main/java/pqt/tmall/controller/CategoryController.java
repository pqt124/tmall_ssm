package pqt.tmall.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import pqt.tmall.pojo.Category;
import pqt.tmall.service.CategoryService;
import pqt.tmall.util.ImageUtil;
import pqt.tmall.util.Page;
import pqt.tmall.util.UploadedImageFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @RequestMapping("admin_category_list")
    public String list(Model model, Page page) {
        PageHelper.offsetPage(page.getStart(),page.getCount());
        List<Category> cs = categoryService.list();
        int total = (int)new PageInfo<>(cs).getTotal();
        page.setTotal(total);
        model.addAttribute("cs", cs);
        model.addAttribute("page", page);
        return "admin/listCategory";
    }

    @RequestMapping("admin_category_delete")
    public String delete(Integer id, HttpSession session) {
        categoryService.delete(id);

        File imageFolder = new File(session.getServletContext().getRealPath("img/category"));
        File file = new File(imageFolder, id+ ".jpg");
        file.delete();
        return "redirect:/admin_category_list";
    }

    @RequestMapping("admin_category_edit")
    public String edit(Model model, Integer id) {
        Category c = categoryService.get(id);
        model.addAttribute("c", c);
        return "admin/editCategory";
}

    @RequestMapping("admin_category_edit_do")
    public String editDo(Category category, HttpSession session, UploadedImageFile uploadedImageFile) throws IOException {
        categoryService.update(category);
        MultipartFile image = uploadedImageFile.getImage();
        File imageFolder = new File(session.getServletContext().getRealPath("img/category"));
        File file = new File(imageFolder, category.getId() + ".jpg");
        if (image!=null&&!image.isEmpty()) {
            file.delete();
            if (!file.getParentFile().exists())
                file.getParentFile().mkdirs();
            image.transferTo(file);
            BufferedImage img = ImageUtil.change2jpg(file);
            ImageIO.write(img, "jpg", file);
        }
        return "redirect:/admin_category_list";
    }

    @RequestMapping("admin_category_add")
    public String add(Category c, HttpSession session, UploadedImageFile uploadedImageFile) throws IOException {
        categoryService.add(c);
        File imageFolder = new File(session.getServletContext().getRealPath("img/category"));
        File file = new File(imageFolder, c.getId() + ".jpg");
        if (!file.getParentFile().exists())
            file.getParentFile().mkdirs();
        uploadedImageFile.getImage().transferTo(file);
        BufferedImage img = ImageUtil.change2jpg(file);
        ImageIO.write(img, "jpg", file);

        return "redirect:/admin_category_list";
    }
}
