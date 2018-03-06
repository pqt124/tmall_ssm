package pqt.tmall.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import pqt.tmall.pojo.User;
import pqt.tmall.pojo.UserExample;
import pqt.tmall.service.UserService;
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
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping("admin_user_list")
    public String list(Model model, Page page,UserExample example) {
        PageHelper.offsetPage(page.getStart(),page.getCount());
        List<User> us = userService.list();
        int total = (int)new PageInfo<>(us).getTotal();
        page.setTotal(total);
        model.addAttribute("us", us);
        model.addAttribute("page", page);
        return "admin/listUser";
    }

    @RequestMapping("admin_user_delete")
    public String delete(Integer id, HttpSession session) {
        userService.delete(id);

        File imageFolder = new File(session.getServletContext().getRealPath("img/user"));
        File file = new File(imageFolder, id+ ".jpg");
        file.delete();
        return "redirect:/admin_user_list";
    }

    @RequestMapping("admin_user_edit")
    public String edit(Model model, Integer id) {
        User c = userService.get(id);
        model.addAttribute("c", c);
        return "admin/editUser";
}

    @RequestMapping("admin_user_edit_do")
    public String editDo(User user, HttpSession session, UploadedImageFile uploadedImageFile) throws IOException {
        userService.update(user);
        MultipartFile image = uploadedImageFile.getImage();
        File imageFolder = new File(session.getServletContext().getRealPath("img/user"));
        File file = new File(imageFolder, user.getId() + ".jpg");
        if (image!=null&&!image.isEmpty()) {
            file.delete();
            if (!file.getParentFile().exists())
                file.getParentFile().mkdirs();
            image.transferTo(file);
            BufferedImage img = ImageUtil.change2jpg(file);
            ImageIO.write(img, "jpg", file);
        }
        return "redirect:/admin_user_list";
    }

    @RequestMapping("admin_user_add")
    public String add(User c, HttpSession session, UploadedImageFile uploadedImageFile) throws IOException {
        userService.add(c);
        File imageFolder = new File(session.getServletContext().getRealPath("img/user"));
        File file = new File(imageFolder, c.getId() + ".jpg");
        if (!file.getParentFile().exists())
            file.getParentFile().mkdirs();
        uploadedImageFile.getImage().transferTo(file);
        BufferedImage img = ImageUtil.change2jpg(file);
        ImageIO.write(img, "jpg", file);

        return "redirect:/admin_user_list";
    }
}
